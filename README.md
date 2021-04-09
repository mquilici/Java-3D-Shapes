# Java-3D-Shapes

<p align="center">
     <img src="/images/Sync.png" alt="alt text" width="480px">
     <img src="/images/Shape2.png" alt="alt text" width="480px">
</p>
<p align="center">
     <img src="/images/Shape3.png" alt="alt text" width="480px">
     <img src="/images/Shape1.png" alt="alt text" width="480px">
</p>

### Description
This is a Java program that I wrote during my free time while studying at SNHU. The program draws 15 different 3D objects by transforming their vertices from object space to screen space using rotation matrices and a perspective projection. After transformation, the points are drawn using standard Java Graphics2D methods. Objects can be rotate dby clicking and dragging or by using the arrow keys. Objects can be changed using buttons on the screen and various settings like like antialiasing can be enabled using checkboxes.

### Why I selected this project
I selected this project to include in my ePortfolio because it a good example of how various data structures and algorithms can be used in concert to achieve a specific task. The program utilizes data structures like vectors, matrices, and a custom node object for various operations. Vectors are used to hold the x, y, and z coordinates of each vertex, matrices are used for rotation operations, and nodes are used for storing polygon coordinates in a binary search tree. To transform the vertices and shade each polygon, a variety of algorithms were used. There are methods to compute a matrix-vector product, vector dot product, vector cross product, etc. With these operations, higher-order methods could be constructed to compute diffuse shading using the angle between the face normal and the light source. Specular highlights are computed using the reflection direction of the light source based on the surface normal of a face, the light source direction, and the viewing direction.

To improve the program, I added a binary search tree (BST) data structure to store and sort the faces for each object so they can be drawn back to front. The advantages of using BSTs include fast searches, fast insertion, and fast deletion. Previously, I used an inefficient selection sort method with a complexity of O(n^2). BSTs, however, have a complexity of O(log n) and are therefore much faster. The BST was implemented by creating a new BinaryTree.java class that contains a node data structure, a method for adding nodes to the tree, and a method for adding traversing the tree in order. Each node stores a 2D “face” array that holds the x, y, and z components for each vertex in a polygon. A depth value is also stored that holds the average depth of all the vertices in the polygon. Lastly, references to left and right nodes contain the children of the current node.

### Course objectives
I exceeded my objectives for this project as I initially intended only intended to implement a new BST sorting algorithm; however, I was also able to improve the GUI and add methods to compute specular lighting on the 3D objects. This involved implementing several new methods to compute reflections. In the future, I intend to put of the vector methods in a separate class to make the program more modular.

### Process of enhancing the program
While implementing the BST class in the artifact, I learned several key facts. The approach I used previously to create a BST in C++ utilized pointers to child nodes. Java does not have pointers, instead objects are accessed through references to their instances. Additional differences include the way in which nodes are accessed. For example, in Java, nodes are accessed using dot notation like node.left or node.right. In C++, the nodes are accessed using arrow notation like node->left or node->right because each child node is a pointer to an object. These differences made the Java implementation easier implement. While switching between C++ and Java can be challenging, I found the process to be helpful for furthering my understanding of both languages.

### Running the program
The following are required to run this program:
- Download the [Java 3D Shapes program](https://github.com/mquilici/Java-3D-Shapes/ShapesGUI.jar)
- Install a [Java Runtime Environment (JRE)](https://www.java.com/en/download/) if necessary.
