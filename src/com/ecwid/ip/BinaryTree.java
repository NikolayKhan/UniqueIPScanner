package com.ecwid.ip;

public class BinaryTree {
    long nodesCount = 0; // here we count nodes
    IPNode rootNode = null; // just root node

    /**
     * method inserts node into binary non-balanced tree
     * in case element exists nothing happens
     * @param ip IP address in String format
     */
    public void insert(String ip){
        IPNode node = new IPNode(ip);
        if (rootNode == null) {
            rootNode = node;
            this.nodesCount++;
        } else {
            IPNode current = rootNode;
            IPNode prev = null;
            boolean stop = false;
            do{
                prev = current;
                switch (node.compareTo(prev.value)) {
                    case LESS:
                    current = current.left;
                    if (current == null) {
                        prev.left = node;
                        this.nodesCount++;
                        stop = true;
                    }
                    break;
                    case GREATER:
                    current = current.right;
                    if (current == null) {
                        prev.right = node;
                        this.nodesCount++;
                        stop = true;
                    }
                    break;
                    case EQUAL:
                    stop = true;
                }
            } while (!stop);
        }
    }
}
