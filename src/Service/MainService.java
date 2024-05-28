package Service;

import java.util.ArrayList;

import DataStr.MyGraph;

public class MainService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyGraph<String> map = new MyGraph<String>();
		try {
			map.addVertice("Ventspils");
			map.addVertice("Rīga");
			map.addVertice("Kuldīga");
			map.addVertice("Talsi");
			map.addVertice("Tukums");
			
			map.addEdge("Ventspils", "Rīga", 189f);
			map.addEdge("Rīga","Ventspils", 189f);
			map.addEdge("Ventspils", "Kuldīga", 56.49f);
			map.addEdge("Ventspils", "Talsi", 64f);
			map.addEdge("Tukums", "Rīga", 58f);
			map.addEdge("Rīga", "Kuldīga", 148.88f);
			
			map.print();
			
			System.out.println("______________________________________________");
			ArrayList<String> path1 = map.searchPathByDepth("Ventspils", "Kuldīga");
			for(String tempS: path1) {
				System.out.print(tempS + "->");
			}System.out.println();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		try {
			System.out.println("------------MINIMUM SPANNING TREE-----------");
			map.minimumSpanningTree("Ventspils");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
