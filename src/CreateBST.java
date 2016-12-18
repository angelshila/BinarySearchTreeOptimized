
public class CreateBST extends Thread {
	
	//Sorted Array
	private static int[] a = {1,3,5,7,9,15,20};
	
	//TreeNode which will hole the BST root
	private static TreeNode root=null;
	
	//Thread Properties
	String threadName;
	int start,end=0;
	TreeNode node;
	
	CreateBST(String name,int s,int e,TreeNode node) {
		this.start=s;
		this.end = e;
		this.threadName = name;
		this.node = node;
	//	System.out.println("Creating " +  threadName);
	}
	
	public static void main(String[] args) throws InterruptedException{
		
		//Calling the BST function
		root = createMinimalBST(a);
		
		//Printing Inorder Traversal of BST to prove that it is BST
		System.out.println("Inrorder Traversal of BST");
		printBST(root);
	
	}
	
	private static void printBST(TreeNode node) {
		
		if(node!=null){
			printBST(node.left);
			System.out.print(node.data+" ");
			printBST(node.right);
		}
		
	}
	
	public static TreeNode createMinimalBST(int array[]) throws InterruptedException {
		return createMinimalBST(array, 0, array.length - 1);
	}

	private static TreeNode createMinimalBST(int arr[], int start, int end) throws InterruptedException{
		
		if (end < start) {
			return null;
		}

		int mid = (start + end) / 2;
		
		//Creating two threads
		CreateBST t1 = new CreateBST("t1",start,mid-1,null);
		CreateBST t2 = new CreateBST("t2",mid+1,end,null);
		TreeNode n = new TreeNode(arr[mid]);
		t1.start();
		t2.start();	
		t1.join();
		t2.join();
		
		//Multi-threaded execution over. Join left and right subtree
		n.left = t1.node;
		n.right = t2.node;
		return n;
	}
	
	
	//Recursive BST function for left and right subtree
	private TreeNode createMinimalBST_(int arr[], int start, int end){
		
		if (end < start) {
			return null;
		}
		int mid = (start + end) / 2;
		TreeNode n = new TreeNode(arr[mid]);
		n.left = createMinimalBST_(arr, start, mid - 1);
		n.right = createMinimalBST_(arr, mid + 1, end);
		return n;
	}

	//Thread run() function
	//Each Thread will call BST function to simultaneously create the left subtree and right subtree for the BST root
    public void run() { 
    	
//    	System.out.println(this.threadName);
    	this.node=this.createMinimalBST_(a, this.start, this.end);

     }


	
	 
}
