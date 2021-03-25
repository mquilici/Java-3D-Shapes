package shapes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import shapes.BinaryTree.Node;

@SuppressWarnings("serial")
public class ShapesGUI extends JFrame implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

	public static ShapesGUI frame;
	private mainDraw draw;
	private Point mousePt;
	private final Action action_prev_object = new SwingAction_Prev();
	private final Action action_next_object = new SwingAction_Next();
	private final Action action_flat_shading = new SwingAction_Flat();
	private final Action action_wire_shading = new SwingAction_Wire();
	private final Action action_transparency = new SwingAction_Trans();
	private final Action action_antialias = new SwingAction_Alias();
	private final Action action_perspective = new SwingAction_Pers();
	private final Action action_color = new SwingAction_Color();
  
    // Define keyPressed callback for actions like rotating the objects
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)     // rotate object right
	    	draw.moveTheta(-5,0);
	    else if (e.getKeyCode() == KeyEvent.VK_LEFT) // rotate object left
	    	draw.moveTheta(5,0);
	    else if (e.getKeyCode() == KeyEvent.VK_DOWN) // rotate object down
	    	draw.moveTheta(0,5);
	    else if (e.getKeyCode() == KeyEvent.VK_UP)   // rotate object up
	    	draw.moveTheta(0,-5);
	    else if (e.getKeyCode() == KeyEvent.VK_P)    // toggle perspective
	    	draw.changePerspective();
	    else if (e.getKeyCode() == KeyEvent.VK_TAB)  // change object
	    	if (e.isShiftDown())
	    		draw.changeShape(-1);
	    	else
	    		draw.changeShape(1);
		draw.setTheta();
	}
	
	// Required callback when keys are released
	public void keyReleased(KeyEvent e) {
	}

	// Required callback when keys are typed
	public void keyTyped(KeyEvent e) {
	}

	// Mouse pressed callback to get the starting point for rotation
	public void mousePressed(MouseEvent e) {
		mousePt = e.getPoint();
	}

	// Mouse released callback sets orientation parameters to their last value
	// otherwise objects will snap back to the orientation before rotating
	public void mouseReleased(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON1 && e.isShiftDown()) {
			draw.setLight();
		} else if (e.getButton()==MouseEvent.BUTTON3) {
			draw.setXY();
		} else {
			draw.setTheta();
		}
	}

	// Required callback when mouse enters bounds of listened-to component
	public void mouseEntered(MouseEvent e) {
	}
	
	// Required callback when mouse exits bounds of listened-to component
	public void mouseExited(MouseEvent e) {
	}

	// Required callback when mouse is clicked
	public void mouseClicked(MouseEvent e) {
	}
   
	// Mouse dragged callback used to rotate and move objects
	public void mouseDragged(MouseEvent e) {

		if (e.getButton()==MouseEvent.BUTTON1 && e.isShiftDown()) {
			// left click with shift down moves the light
			double dx = e.getX() - mousePt.x;
			double dy = e.getY() - mousePt.y;
			draw.moveLight(dx,dy);
		} else if (e.getButton()==MouseEvent.BUTTON3) {
			// right click translates object
			double dx = e.getX() - mousePt.x;
			double dy = e.getY() - mousePt.y;
			draw.moveXY(dx,dy);
		} else {
			// all other click types rotate the object
			double dx = e.getX() - mousePt.x;
			double dy = e.getY() - mousePt.y;
			draw.moveTheta(-dx,dy);
		}
	}
   
	// Required callback when mouse is moved
	public void mouseMoved(MouseEvent e) {
	}

	// Mouse wheel callback used to scale an object or change perspective
    public void mouseWheelMoved(MouseWheelEvent e) {
    	if (e.isShiftDown()) {
    		// Using shift with the mouse wheel changes the perspective distortion
        	if (e.getWheelRotation() < 0) {
        		draw.moveZ(-1);
        	} else {
        		draw.moveZ(1);
            }
    	} else {
    		// Mouse wheel alone scales the object
	    	if (e.getWheelRotation() < 0) {
	    		draw.zoom(1);
	    	} else {
	    		draw.zoom(-1);
	        }
    	}
    }

    // Method to implement GUI components
	public ShapesGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.draw = new mainDraw();
		
		// Add listeners
	    addKeyListener(this);
	    addMouseListener(this);
	    addMouseMotionListener(this);
	    addMouseWheelListener(this);
	    setFocusable(true);
	    setFocusTraversalKeysEnabled(false);
	    
	    // Define bottom panel for buttons
	    JPanel bottom_panel = new JPanel();
	    bottom_panel.setBackground(Color.BLACK);
	    getContentPane().add(bottom_panel, BorderLayout.SOUTH);
	    
	    	// Define button for previous object
		    JButton btnPreviousObject = new JButton("Previous Object");
		    btnPreviousObject.setFocusable(false);
		    bottom_panel.add(btnPreviousObject);
		    btnPreviousObject.setAction(action_prev_object);
		    
		    // Define button for next object
		    JButton btnNextObject = new JButton("Next Object");
		    btnNextObject.setFocusable(false);
		    bottom_panel.add(btnNextObject);
		    btnNextObject.setAction(action_next_object);
	    
		// Define top panel for buttons
	    JPanel top_panel = new JPanel();
	    top_panel.setBackground(Color.BLACK);
	    getContentPane().add(top_panel, BorderLayout.NORTH);
	    
	    	// Define check box for enabling flat shading
		    JCheckBox chckbxFlatShading = new JCheckBox("Flat Shading");
		    chckbxFlatShading.setFocusable(false);
		    top_panel.add(chckbxFlatShading);
		    chckbxFlatShading.setSelected(mainDraw.enableShading);
		    chckbxFlatShading.setAction(action_flat_shading);
		    chckbxFlatShading.setForeground(Color.WHITE);
		    
	    	// Define check box for enabling wireframe shading
		    JCheckBox chckbxWireframe = new JCheckBox("Wireframe");
		    chckbxWireframe.setFocusable(false);
		    top_panel.add(chckbxWireframe);
		    chckbxWireframe.setSelected(mainDraw.enableWireframe);
		    chckbxWireframe.setForeground(Color.WHITE);
		    chckbxWireframe.setAction(action_wire_shading);
		    
		    // Define check box for enabling transparency
		    JCheckBox chckbxTransparency = new JCheckBox("Transparency");
		    chckbxTransparency.setFocusable(false);
		    top_panel.add(chckbxTransparency);
		    chckbxTransparency.setSelected(mainDraw.enableTransparency);
		    chckbxTransparency.setForeground(Color.WHITE);
		    chckbxTransparency.setAction(action_transparency);
		    
		    // Define check box for enabling antialiasing (slow)
		    JCheckBox chckbxAntialias = new JCheckBox("Antialiasing");
		    chckbxAntialias.setFocusable(mainDraw.enableAntialias);
		    top_panel.add(chckbxAntialias);
		    chckbxAntialias.setForeground(Color.WHITE);
		    chckbxAntialias.setAction(action_antialias);
		    
		    // Define check box for enabling perspective
		    JCheckBox chckbxPerspective = new JCheckBox("Perspective");
		    chckbxPerspective.setFocusable(false);
		    top_panel.add(chckbxPerspective);
		    chckbxPerspective.setSelected(mainDraw.enablePerspective);
		    chckbxPerspective.setForeground(Color.WHITE);
		    chckbxPerspective.setAction(action_perspective);
		    
		     // Define check box for enabling object coloring
		    JCheckBox chckbxColor = new JCheckBox("Color");
		    chckbxColor.setFocusable(false);
		    top_panel.add(chckbxColor);
		    chckbxColor.setSelected(mainDraw.enableColor);
		    chckbxColor.setForeground(Color.WHITE);
		    chckbxColor.setAction(action_color);
	}

	// Main method for GUI class
	public static void main(String[] args) {
		
		// Define maximum window size as a fraction of screen size
		float windowToScreenScale = 0.85f;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenHeight = screenSize.getHeight();
		double screenWidth = screenSize.getWidth();
		int width = (int)(screenSize.getWidth()*windowToScreenScale);
		int height = (int)(screenSize.getHeight()*windowToScreenScale);
		
		// Get center of window
		int x0 = (int) screenWidth/2 - width/2;
		int y0 = (int) screenHeight/2 - height/2;
		
		// Create window frame
		frame = new ShapesGUI();
		frame.setResizable(true);
		frame.setBounds(x0, y0, width, height);
		frame.setMinimumSize(new Dimension(640, 480));
		frame.setPreferredSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(frame.draw);
		frame.setTitle("3D Shape Demonstration");
		frame.pack();
		frame.setVisible(true);
	}
	
	// Define action for press of previous object button
	private class SwingAction_Prev extends AbstractAction {
		public SwingAction_Prev() {
			putValue(NAME, "<--");
		}
		public void actionPerformed(ActionEvent e) {
			draw.changeShape(-1);
		}
	}
	
	// Define action for press of next object button
	private class SwingAction_Next extends AbstractAction {
		public SwingAction_Next() {
			putValue(NAME, "-->");
		}
		public void actionPerformed(ActionEvent e) {
			draw.changeShape(1);
		}
	}
	
	// Define action for press of flat shading check box
	private class SwingAction_Flat extends AbstractAction {
		public SwingAction_Flat() {
			putValue(NAME, "Flat Shading");
		}
		public void actionPerformed(ActionEvent e) {
			draw.setFlatShading();
		}
	}
	
	// Define action for press of wireframe shading check box
	private class SwingAction_Wire extends AbstractAction {
		public SwingAction_Wire() {
			putValue(NAME, "Wireframe");
		}
		public void actionPerformed(ActionEvent e) {
			draw.setWireframeShading();
		}
	}
	
	// Define action for press of transparency check box
	private class SwingAction_Trans extends AbstractAction {
		public SwingAction_Trans() {
			putValue(NAME, "Transparency");
		}
		public void actionPerformed(ActionEvent e) {
			draw.setTransparency();
		}
	}
	
	// Define action for press of antialiasing check box
	private class SwingAction_Alias extends AbstractAction {
		public SwingAction_Alias() {
			putValue(NAME, "Antialiasing");
		}
		public void actionPerformed(ActionEvent e) {
			draw.setAntialias();
		}
	}
	
	// Define action for press of perspective check box
	private class SwingAction_Pers extends AbstractAction {
		public SwingAction_Pers() {
			putValue(NAME, "Perspective");
		}
		public void actionPerformed(ActionEvent e) {
			draw.changePerspective();
		}
	}
	
	// Define action for press of color check box
	private class SwingAction_Color extends AbstractAction {
		public SwingAction_Color() {
			putValue(NAME, "Color");
		}
		public void actionPerformed(ActionEvent e) {
			draw.changeColor();
		}
	}
}


// Main draw component for drawing objects
@SuppressWarnings("serial")
class mainDraw extends JComponent {

	private static Graphics2D g2d;
	private static Shapes currentModel;
	private static Random rand = new Random();
	private static int window_width;
	private static int window_height;
	
	// Object properties
	private static int objScale0 = 200;              // number of screen pixels per unit object length
	private static int objScaleMin = 10;             // clamp minimum zoom level
	private static int objScaleMax = 1000;           // clamp maximum zoom level
	
	private static double[] objRot = {0,-60};        // object rotation angle {horizontal, vertical}
	private static double[] objRot0 = {0,-60};       // initial rotation (needed when user changes rotation)
	private static double[] objPos = {0,0,4};        // object position {x, y, z}
	private static double[] objPos0 = {0,0,4};       // initial position (needed when user changes position)
	private static double objMinDistance = 2;        // clamp minimum distance to object
	private static double objMaxDistance = 10;       // clamp maximum distance to object
	private static int objType = 0;                  // number indicating which shape to draw
	private static int objLast = 0;                  // last shape drawn (prevents reloading)
	
	private static float objTransparency = 1.0f;     // object transparency level
	private static Color objLineColor = Color.black; // object line color
	private static int[] objColor = {150,150,255};   // initial object color
	
	// Lighting properties
	private static double[] lightPos = {-1,2,5};     // light position
	private static double[] lightPos0 = {-1,2,5};    // initial position (needed when user changes position)
	private static double lightAmbient = 0.2;        // overall light level must be > 0
	private static double lightDiffuse = 0.6;        // light intensity... must be > 0
	private static double lightSpecular = 0.5;       // specular intensity... must be > 0
	private static double lightShininess = 32;       // highlight... must be > 1

	// Panel controls
	public static boolean enableWireframe = false;
	public static boolean enableShading = true;
	public static boolean enableTransparency = false;
	public static boolean enableAntialias = false;
	public static boolean enablePerspective = true;
	public static boolean enableColor = true;
	

	// Paint mechanism for drawing shapes
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Get frame size
	    Rectangle window = ShapesGUI.frame.getBounds();
	    window_width = window.width;
	    window_height = window.height;
		
	    // Setup and draw shapes
		g2d = (Graphics2D) g;
	    g2d.fillRect(0, 0, window_width, window_height);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, objTransparency));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	    drawShapes();
	}
	
	// Method for translating an object
	public void moveXY(double dx, double dy) {
		double speedScale = 255; 
		objPos[0] = objPos0[0] + dx/speedScale;
		objPos[1] = objPos0[1] + dy/speedScale;
		repaint();
	}
	
	// Method set initial coordinates to current coordinates
	public void setXY() {
		objPos0[0] = objPos[0];
		objPos0[1] = objPos[1];
	}
	
	// Method to move the light source
	public void moveLight(double dx, double dy) {
		double speedScale = 255; 
		lightPos[0] = lightPos0[0] - dx/speedScale;
		lightPos[1] = lightPos0[1] - dy/speedScale;
		repaint();
	}
	
	// Method to set initial light coordinates to current coordinates
	public void setLight() {
		lightPos0[0] = lightPos[0];
		lightPos0[1] = lightPos[1];
	}
	
	// Method to move camera in and out - this alters perspective
	public void moveZ(double factor) {
		double initial = objPos[2];
		double moveScale = 10;
		objPos[2] += factor/moveScale;
		if (objPos[2] < objMinDistance || objPos[2] > objMaxDistance) {
			objPos[2] = initial;
		}
		repaint();
	}
	
	// Method to rotate an object horizontally and vertically
	public void moveTheta(double tx, double ty) {
		double rotateScale = 5;
		objRot[0] = objRot0[0] + tx/rotateScale;
		objRot[1] = objRot0[1] + ty/rotateScale;
		objRot[1] = (objRot[1] > 0) ? 0 : objRot[1];
		objRot[1] = (objRot[1] < -180) ? -180 : objRot[1];
		repaint();
	}
	
	// Method to set initial rotation angles to current angles
	public void setTheta() {
		objRot0[0] = objRot[0];
		objRot0[1] = objRot[1];
	}
	
	// Method to scale the object
	public void zoom(double step) {
		int scalefactor0 = objScale0;
		double zoomScale = 20;
		objScale0 += step*zoomScale;
		if (objScale0 < objScaleMin || objScale0 > objScaleMax) {
			objScale0 = scalefactor0;
		}
		repaint();
	}
	
	// Method to turn on or off perspective projection
	public void changePerspective() {
		enablePerspective = !enablePerspective;
		repaint();
	}
	
	// Method to change to a different shape
	public void changeShape(int i) {
		int numShapes = 15;
		objType = (objType + i) % numShapes;
		objType = (objType < 0) ? numShapes+objType : objType;
		applyColor(); // remove for same color across all objects
		repaint();
	}
	
	// Method to change the color of an object
	public void changeColor() {
		enableColor = !enableColor;
		applyColor();
		repaint();
	}
	
	// Applies a random color to objects
	public void applyColor() {
		int lightness = 100; // decrease for more saturated colors

		if (enableColor) {
			objColor[0] = lightness+rand.nextInt(255-lightness);
			objColor[1] = lightness+rand.nextInt(255-lightness);
			objColor[2] = lightness+rand.nextInt(255-lightness);
		} else {
			objColor[0] = 255;
			objColor[1] = 255;
			objColor[2] = 255;
		}
	}
	
	// Method to enable antialiasing
	public void setAntialias() {
		enableAntialias = !enableAntialias;
		repaint();
	}
	
	// Method to enable flat shading
	public void setFlatShading() {
		enableShading = !enableShading;
		changeLineColor();
	}
	
	// Method to enable wireframe shading
	public void setWireframeShading() {
		enableWireframe = !enableWireframe;
		changeLineColor();
	}
	
	// Method to change the line color based on shading type
	private void changeLineColor() {
		// Use white wireframe when flat shading is disabled
		if (enableShading & enableWireframe) {
			objLineColor = Color.black;
		} else {
			objLineColor = Color.white;
		}
		repaint();
	}
	
	// Method to enable transparency
	public void setTransparency() {
		enableTransparency = !enableTransparency;
		if (enableTransparency) {
			objTransparency = 0.8f;
		} else { 
			objTransparency = 1f;
		}
		repaint();
	}
	
	// Method to load and draw various shapes from the Polyhedron class
	private static void drawShapes() {
		
		// Enable or disable antialiasing
		if (enableAntialias) {
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		} else {
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		}
		
		// Setup labels to describe what object is displayed
		g2d.setColor(Color.WHITE);
		int topLabelMargin = 100;
		FontMetrics fm = g2d.getFontMetrics();
		String titleText = "";
		
		// Load the object selected by the user
		switch (objType) {
			case 0:
				// Animate by recomputing when object is moving
				titleText = "cos(r^2)^2*exp(-r^2)";
				currentModel = drawFunction();
				break;
			case 1:
				titleText = "Sphere";
				if (objType != objLast)
					currentModel = drawSphere();
				break;
			case 2:
				titleText = "Cube";
				if (objType != objLast)
					currentModel = Shapes.cube;
				break;
			case 3:	
				titleText = "Dodecahedron";
				if (objType != objLast)
					currentModel = Shapes.dodecahedron;
				break;
			case 4:
				titleText = "Icosahedron";
				if (objType != objLast)
					currentModel = Shapes.icosahedron;
				break;
			case 5:
				titleText = "Octahedron";
				if (objType != objLast)
					currentModel = Shapes.octahedron;
				break;
			case 6:
				titleText = "Rhombic Dodecahedron";
				if (objType != objLast)
					currentModel = Shapes.rhombicDodecahedron;
				break;
			case 7:
				titleText = "Soccer Ball";
				if (objType != objLast)
					currentModel = Shapes.socerBall;
				break;
			case 8:
				titleText = "Stellated Dodecahedron";
				if (objType != objLast)
					currentModel = Shapes.stellatedDodecahedron;
				break;
			case 9:
				titleText = "Stellated Icosahedron";
				if (objType != objLast)
					currentModel = Shapes.stellatedIcosahedron;
				break;
			case 10:
				titleText = "Stellated Octahedron";
				if (objType != objLast)
					currentModel = Shapes.stellatedOctahedron;
				break;
			case 11:
				titleText = "Tetrahedron";
				if (objType != objLast)
					currentModel = Shapes.tetrahedron;
				break;
			case 12:
				titleText = "Truncated Icosahedron";
				if (objType != objLast)
					currentModel = Shapes.truncatedIcosahedron;
				break;
			case 13:
				titleText = "Truncated Rhombic Dodecahedron";
				if (objType != objLast)
					currentModel = Shapes.truncatedRhombicDodecahedron;
				break;
			case 14:
				titleText = "Stellated Dodecahedron";
				if (objType != objLast)
					currentModel = Shapes.stellatedDodecahedron;
				break;
			default:
				break;
		}
		
		// Set flag to indicate if a new object is selected or not
		// otherwise the same object will load again every frame
		objLast = objType;
		
		// Draw title
		g2d.drawString(titleText, (window_width-fm.stringWidth(titleText))/2, window_height-topLabelMargin);
		
		// Draw polygons
		drawFaces(currentModel.vertices, currentModel.faces, currentModel.normals);
	}
	

	// Method that defines the mathematical function to draw for the first object
	private static double function(double x, double y) {
		double nWaves = 3;
		double decay = 2;
		double speed = 4;
		return -Math.pow(Math.cos(nWaves*(x*x + y*y)+Math.toRadians(objRot[0])*speed),2)*Math.exp(-decay*(x*x + y*y));
	}
	
	// Method to draw to draw the mathematical function
	private static Shapes drawFunction() {
		
		// resolution of plot
		int nx = 100;
		int ny = 100;
		
		// range of plot
		double[] xrange = {-1.5, 1.5};
		double[] yrange = {-1.5, 1.5};
		
		// arrays to hold faces and vertices
		int[][] objFaces = new int[nx*ny][4];
		double[][] objVertices = new double[nx*ny*4][3];
		double[][] objNormals = new double[nx*ny][3];
		
		// size of a single polygon
		double dx = (xrange[1]-xrange[0])/nx;
		double dy = (yrange[1]-yrange[0])/ny;

		// loop over x and y to determine the vertices and faces needed to draw the object
		for (int j=0; j<ny; ++j) {
			for (int i=0; i<nx; ++i) {
				
				int f = j*nx + i;
				int v = 4*f;
				
				// first vertex
				objVertices[v+0][0] = xrange[0]+i*dx;
				objVertices[v+0][1] = yrange[0]+j*dy;
				objVertices[v+0][2] = function(objVertices[v+0][0], objVertices[v+0][1]);
				
				// second vertex
				objVertices[v+1][0] = xrange[0]+(i+1)*dx;
				objVertices[v+1][1] = yrange[0]+j*dy;
				objVertices[v+1][2] = function(objVertices[v+1][0], objVertices[v+1][1]);
				
				// third vertex
				objVertices[v+2][0] = xrange[0]+(i+1)*dx;
				objVertices[v+2][1] = yrange[0]+(j+1)*dy;
				objVertices[v+2][2] = function(objVertices[v+2][0], objVertices[v+2][1]);
				
				// fourth vertex
				objVertices[v+3][0] = xrange[0]+i*dx;
				objVertices[v+3][1] = yrange[0]+(j+1)*dy;
				objVertices[v+3][2] = function(objVertices[v+3][0], objVertices[v+3][1]);
				
				int face[] = {v+0, v+1, v+2, v+3}; // define face
				
				objFaces[f] = face;                // add face to array
				
				// get edges used to compute normal
				double edge1[] = subtract(objVertices[v+1], objVertices[v+0]);
				double edge2[] = subtract(objVertices[v+2], objVertices[v+1]);
				
				// compute normal vector using cross product
				objNormals[f] = normalize(crossProduct(edge1,edge2));
	    	}
    	}
    	
		return new Shapes(objVertices, objFaces, objNormals);
	}	
	
	// Method to draw a sphere
	private static Shapes drawSphere() {
	    
		// sphere properties
		int nt = 48;              // theta resolution
		int np = 24;              // phi resolution
		double dt = 2*Math.PI/nt; // delta theta angle
		double dp = Math.PI/np;   // delta phi angle
	    double radius = 1;        // radius
		
		// arrays to hold faces and vertices
		int[][] objFaces = new int[nt*np][4];
		double[][] objVertices = new double[nt*np*4][3];
		double[][] objNormals = new double[nt*np][3];

		// loop over theta and phi angles and generate face definitions
    	for (int t=0; t<nt; ++t) {
			for(int p=0; p<np; ++p) {
				
				int f = t*np + p;
				int v = 4*f;
				
				// first quad vertex
				objVertices[v+0][0] = radius * Math.sin(dp*p)*Math.cos(dt*t);
				objVertices[v+0][1] = radius * Math.sin(dp*p)*Math.sin(dt*t);
				objVertices[v+0][2] = radius * Math.cos(dp*p);
				
				// second quad vertex
				objVertices[v+1][0] = radius * Math.sin(dp*p)*Math.cos(dt*(t+1));
				objVertices[v+1][1] = radius * Math.sin(dp*p)*Math.sin(dt*(t+1));
				objVertices[v+1][2] = radius * Math.cos(dp*p);
					
				// third quad vertex
				objVertices[v+2][0] = radius * Math.sin(dp*(p+1))*Math.cos(dt*(t+1));
				objVertices[v+2][1] = radius * Math.sin(dp*(p+1))*Math.sin(dt*(t+1));
				objVertices[v+2][2] = radius * Math.cos(dp*(p+1));
				
				// fourth quad vertex
				objVertices[v+3][0] = radius * Math.sin(dp*(p+1))*Math.cos(dt*t);
				objVertices[v+3][1] = radius * Math.sin(dp*(p+1))*Math.sin(dt*t);
				objVertices[v+3][2] = radius * Math.cos(dp*(p+1));
				
				// Fix hole at bottom due to side with zero length by changing vertex order
				if (p == 0) {
					
					// first quad vertex
					objVertices[v+0][0] = radius * Math.sin(dp*(p+1))*Math.cos(dt*(t+1));
					objVertices[v+0][1] = radius * Math.sin(dp*(p+1))*Math.sin(dt*(t+1));
					objVertices[v+0][2] = radius * Math.cos(dp*(p+1));
					
					// second quad vertex
					objVertices[v+2][0] = radius * Math.sin(dp*p)*Math.cos(dt*t);
					objVertices[v+2][1] = radius * Math.sin(dp*p)*Math.sin(dt*t);
					objVertices[v+2][2] = radius * Math.cos(dp*p);
					
					// third quad vertex
					objVertices[v+3][0] = radius * Math.sin(dp*p)*Math.cos(dt*(t+1));
					objVertices[v+3][1] = radius * Math.sin(dp*p)*Math.sin(dt*(t+1));
					objVertices[v+3][2] = radius * Math.cos(dp*p);
					
					// fourth quad vertex
					objVertices[v+1][0] = radius * Math.sin(dp*(p+1))*Math.cos(dt*t);
					objVertices[v+1][1] = radius * Math.sin(dp*(p+1))*Math.sin(dt*t);
					objVertices[v+1][2] = radius * Math.cos(dp*(p+1));
				}

				int face[] = {v+0, v+1, v+2, v+3}; // define face
				
				objFaces[f] = face; // add face to array
				
				// get edges used to compute normal
				double edge1[] = subtract(objVertices[v+1], objVertices[v+0]);
				double edge2[] = subtract(objVertices[v+2], objVertices[v+1]);
	    		
				// compute normal vector using cross product
				objNormals[f] = normalize(crossProduct(edge1,edge2));
	    	}
    	}
    	
    	return new Shapes(objVertices, objFaces, objNormals);
	}
	
	// Function to draw multiple faces
	private static void drawFaces(double[][] vertices, int[][] faces, double[][] normals) {
	    double[][] face;
		int nfaces = faces.length;
		int nVertices = 0;
		double avgz = 0;
		
		// create binary tree object to hold faces
		BinaryTree bt = new BinaryTree();
		
		// loop over faces to compute their distances and add them to the binary tree
    	for (int f=0; f<nfaces; ++f) {

    		nVertices = faces[f].length;
    		face = new double[nVertices][3];
    		
    		// add vertices to face
			for(int v=0; v<nVertices; ++v) {
				face[v][0] = vertices[(faces[f][v])][0];
				face[v][1] = vertices[(faces[f][v])][1];
				face[v][2] = vertices[(faces[f][v])][2];
			}
			
			// transform face by rotating and translating
			face = transformFace(face);
			
			// transform normal by rotating and translating
    		double[] norm = transformVertex(normals[f]);
			
			// compute the average distance to a given face
			avgz = averageDistance(face);

			// create a new face node for the binary tree using the negative of the distance
			Node node = new Node(face, norm, -avgz);
			
			// add the face to the binary tree
			bt.add(node);
    	}
    	
    	// traverse the binary tree in order to draw faces back to front
    	bt.InOrder();
	}
	
	// Method to draw a single face
	public static void drawFace(double[][] face, double[] normal) {
	    int[] xarr;
	    int[] yarr;
		int nVertices;
		double[] projectedFace;
		
		// get the number of points per face could be more than three for shapes like a cube
    	nVertices = face.length;
    	
    	// arrays to hold x and y screen space vertex locations
	    xarr = new int[nVertices+1];
	    yarr = new int[nVertices+1];
	    
		// loop over vertices and project them to screen space
		for(int i=0; i<nVertices; ++i) {
			
			projectedFace = face[i]; // face projected to screen space
			
			// perform a perspective projection to screen space
			if (enablePerspective) {
				projectedFace = perspectiveTransform(face[i]);
			}
			
			// scale object to screen
			projectedFace = scale(projectedFace, objScale0);
			
			// translate object to center of screen
			xarr[i] = (int) (window_width/2 + projectedFace[0]);
			yarr[i] = (int) (window_height/2 + projectedFace[1]);
		}
		
		// repeat initial point to close polygon (needed for drawPoly)
		xarr[nVertices] = xarr[0];
		yarr[nVertices] = yarr[0];
		
		// if shading is enabled call shade() to set shading color based on orientation
		if (enableShading) {
			shade(face, normal);
			g2d.fillPolygon(xarr, yarr, nVertices+1);
		}
		
		// if wireframe is enabled, draw wireframe around polygons
		if (enableWireframe) {
			g2d.setColor(objLineColor);
			g2d.drawPolyline(xarr, yarr, nVertices+1);
		}
	}
	
	// Method to determine polygon shading based on orientation and lighting
	private static void shade(double[][] face, double[] normal) {
		
		// compute directions needed for light calculations
		double[] facePos = averagePosition(face);                   // average position of face
		double[] viewDir = normalize(subtract(objPos, facePos));    // view direction to face
		double[] lightDir = normalize(subtract(lightPos, facePos)); // light direction to face
		double[] refDir = reflect(lightDir, normal);                // reflection direction of light from face

		// ambient component
		double amb = Math.max(lightAmbient, 0);
		
		// diffuse component
		double dif = lightDiffuse * Math.max(dotProduct(normal, lightDir), 0);

		// specular component
		double spec = lightSpecular * Math.pow(Math.max(dotProduct(viewDir, refDir), 0), lightShininess);
	
		// compute color values based on lighting components
		int cr = (int) Math.min((amb + dif)*objColor[0] + spec*255, 255); // clamp values to 255
		int cg = (int) Math.min((amb + dif)*objColor[1] + spec*255, 255); // clamp values to 255
		int cb = (int) Math.min((amb + dif)*objColor[2] + spec*255, 255); // clamp values to 255
		
		// set color value
		g2d.setColor(new Color(cr, cg, cb));
	}
	
	// Method to transform a face using rotation and translation
	private static double[][] transformFace(double[][] face){
		
		// loop over all vertices
		for (int v=0; v<face.length; ++v) {
			face[v] = transformVertex(face[v]);
		}
		
		return face;
	}
	
	// Method to transform a vertex using rotation and translation
	private static double[] transformVertex(double[] vec){
		
		// rotate object horizontally and then vertically
		vec = rotateZX(vec, objRot[0], objRot[1]);
		
		// translate object using right mouse button
		vec = translate(vec, objPos[0], objPos[1], 0);

		return vec;
	}
	
	// Method to compute the average distance to a face
	private static double averageDistance(double[][] face){
		double avgz = 0;
		
		// loop over vertices to compute the sum of distances
		for (int p=0; p<face.length; ++p) {				
			avgz = avgz + face[p][2];					
		}
		
		// avgz = avgz / face.length; // average is not necessary for sorting purposes
		return avgz;
	}
	
	// Method to compute the average position of a face
	private static double[] averagePosition(double[][] face){
		double pos[] = {0,0,0};
		int nVertices = face.length;
		
		// loop over vertices to compute the sum of distances
		for (int p=0; p<nVertices; ++p) {		
			pos[0] += face[p][0];
			pos[1] += face[p][1];
			pos[2] += face[p][2];				
		}
		
		if (nVertices != 0) {
			// compute average position
			return scale(pos, 1.0/face.length);
		} else {
			// bad face definition
			return pos;
		}
	}
	
	// Method to compute the reflection vector (assuming norm and light are normalized)
	private static double[] reflect(double[] norm, double[] light){
		double ref[] = normalize( subtract( scale(norm, 2*dotProduct(norm, light)), light));
		return ref;
	}
	
	// Method to normalize a vector
	private static double[] normalize(double[] vec) {
		double mag = magnitude(vec);
		if (mag != 0) {
			return scale(vec, 1.0/mag);
		} else {
			return vec;
		}
	}
	
	// Method to compute the magnitude of a vector
	private static double magnitude(double[] vec){
		double mag = Math.sqrt(vec[0]*vec[0] + vec[1]*vec[1] + vec[2]*vec[2]);;
		return mag;
	}
	
	// Method to translate a vector
	private static double[] translate(double[] vec, double x, double y, double z) {
		double vec2[] = {vec[0]+x, vec[1]+y, vec[2]+z};
		return vec2;
	}
	
	// Method to scale a vector
	private static double[] scale(double[] vec, double s) {
		double vec2[] = {vec[0]*s, vec[1]*s, vec[2]*s};
		return vec2;
	}
	
	// Method to subtract vectors
	private static double[] subtract(double[] vec1, double[] vec2) {
		double sub[] = {vec1[0]-vec2[0], vec1[1]-vec2[1], vec1[2]-vec2[2]};
		return sub;
	}
	
	// Method to compute the vector dot product
	private static double dotProduct(double[] vec1, double[] vec2){
		double dot = vec1[0]*vec2[0] + vec1[1]*vec2[1] + vec1[2]*vec2[2];
		return dot;
	}
	
	// Method to compute the vector cross product
	private static double[] crossProduct(double[] vec1, double[] vec2){
		double newVec[] = {vec1[1]*vec2[2] - vec1[2]*vec2[1],
				           vec1[2]*vec2[0] - vec1[0]*vec2[2],
				           vec1[0]*vec2[1] - vec1[1]*vec2[0]};
		return newVec;
	}
	
	// Method to compute a simple perspective projection
	private static double[] perspectiveTransform(double[] vec){
		double bias = 0.1; // prevents polygons from exploding

		double denominator = objPos[2]+vec[2]+bias;
		
		// check for division by zero
		double factor = denominator != 0 ? objPos[2]/denominator : denominator;
		
		// return perspective transformed vector
		return scale(vec, factor);
	}

	// Method to perform combined rotations about z then x
	private static double[] rotateZX(double[] vec, double thetaz, double thetax) {
		// convert angles to radians
		double tx = Math.toRadians(thetax);
		double tz = Math.toRadians(thetaz);
		
		// compute sin and cos of angles
		double cz = Math.cos(tz);
		double cx = Math.cos(tx);
		double sz = Math.sin(tz);
		double sx = Math.sin(tx);
		
		// rotate vector
		double[] newVec = {vec[0]*cz - vec[1]*sz, 
				           vec[1]*cx*cz - vec[2]*sx + vec[0]*cx*sz,
				           vec[2]*cx + vec[1]*cz*sx + vec[0]*sx*sz};
		return newVec;
	}
	
}