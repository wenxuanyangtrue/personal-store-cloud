package top.xsword.system_service.config;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Author: ywx
 * Create Time: 2023/3/7
 * Description:
 */
public class Test {
    public static void main(String[] args) {
        List<Node> nodeList = new ArrayList<>();
        int a = 0;
        Node node1 = new Node();
        node1.id = "1";
        node1.pid = null;
        node1.value = "1";
        Node node2 = new Node();
        node2.id = "2";
        node2.pid = "1";
        node2.value = "2";
        Node node3 = new Node();
        node3.id = "3";
        node3.pid = null;
        node3.value = "3";
        nodeList.add(node1);
        nodeList.add(node2);
        nodeList.add(node3);

        System.out.println(new Gson().toJson(nodeList));

        List<Node> change = change(nodeList);

        System.out.println(new Gson().toJson(change));
    }

    public static List<Node> change(List<Node> nodeList) {
        List<Node> newNodeList = new ArrayList<>();
        nodeList.forEach((node) -> {
            if (node.pid == null) {
                newNodeList.add(node);
                getChildren(nodeList, node);
            }
        });
        return newNodeList;
    }

    public static void getChildren(List<Node> nodeList, Node pNode) {
        pNode.children = new ArrayList<>();
        for (int i = 0; i < nodeList.size(); i++) {
            if(nodeList.get(i).pid != null && nodeList.get(i).pid.equals(pNode.id)){
                pNode.children.add(nodeList.get(i));
                getChildren(nodeList,nodeList.get(i));
            }
        }
    }

    static class Node {
        String id;
        String pid;
        String value;
        List<Node> children;
    }
}
