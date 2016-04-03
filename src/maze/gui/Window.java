package maze.gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import maze.logic.Maze;
import maze.logic.MazeBuilder;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window {

	private JFrame frame;
	private JRootPane rootPane;

	private JLabel lblMazeSize;
	private JTextField txtMazeSize;
	private JLabel lblNumberOfDragons;
	private JTextField txtNumberOfDragons;
	private JCheckBox randomDragons;
	private JLabel lblDragonMode;
	private JComboBox<String> cbDragonMode;
	private JTextArea mazeArea;
	private JLabel status;

	private JButton btnNewMaze;
	private JButton btnExitGame;
	private JButton btnUp;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnDown;
	private KeyStroke upKeyStroke;
	private KeyStroke downKeyStroke;
	private KeyStroke rightKeyStroke;
	private KeyStroke leftKeyStroke;

	private Maze maze;
	private GraphicInterface gi = new GraphicInterface();
	private int mazeSize = 11;
	private int numberDragons = 1;
	
	private JCheckBox cbBuilder;
	private JCheckBox graphicMazeSel;
	private GraphicMaze GM;
	private boolean graphicMode;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Maze Game");
		frame.setBounds(100, 100, 561, 501);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		initializeRootPane();
		initializeMazeOptions();
		initializeButtons();
		
		mazeArea = new JTextArea();
		mazeArea.setFont(new Font("Courier New", Font.PLAIN, 13));
		mazeArea.setEditable(false);
		mazeArea.setBounds(54, 168, 260, 260);
		frame.getContentPane().add(mazeArea);

		// Initializes the status label
		status = new JLabel("Create a new maze to play.");
		status.setFont(new Font("Tahoma", Font.PLAIN, 14));
		status.setBounds(54, 432, 260, 25);
		frame.getContentPane().add(status);
	}

	public void closeInputs()
	{
		btnRight.setEnabled(false);
		btnLeft.setEnabled(false);
		btnUp.setEnabled(false);
		btnDown.setEnabled(false);
	}

	public void initializeRootPane()
	{
		rootPane = frame.getRootPane();

		Action upKeyAction = new AbstractAction("upKeyAction") {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnUp.doClick();
			} 
		};

		Action downKeyAction = new AbstractAction("downKeyAction") {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnDown.doClick();
			}
		};

		Action rightKeyAction = new AbstractAction("rightKeyAction") {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnRight.doClick();
			} 
		};

		Action leftKeyAction = new AbstractAction("leftKeyAction") {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnLeft.doClick();
			}
		};

		upKeyStroke = KeyStroke.getKeyStroke("UP");
		downKeyStroke = KeyStroke.getKeyStroke("DOWN");
		rightKeyStroke = KeyStroke.getKeyStroke("RIGHT");
		leftKeyStroke = KeyStroke.getKeyStroke("LEFT");

		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(upKeyStroke, "upKeyAction");
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(downKeyStroke, "downKeyAction");
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(rightKeyStroke, "rightKeyAction");
		rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(leftKeyStroke, "leftKeyAction");
		rootPane.getActionMap().put("upKeyAction", upKeyAction);
		rootPane.getActionMap().put("downKeyAction", downKeyAction);
		rootPane.getActionMap().put("rightKeyAction", rightKeyAction);
		rootPane.getActionMap().put("leftKeyAction", leftKeyAction);
	}

	public void initializeMazeOptions() {
		// Initializes the label and field related to the Maze Size
		lblMazeSize = new JLabel("Maze Size");
		lblMazeSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMazeSize.setBounds(54, 35, 67, 26);
		frame.getContentPane().add(lblMazeSize);

		txtMazeSize = new JTextField();
		txtMazeSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMazeSize.setText("11");
		txtMazeSize.setBounds(191, 38, 86, 20);
		frame.getContentPane().add(txtMazeSize);
		txtMazeSize.setColumns(10);

		// Initializes the label and field related to the number of Dragons
		lblNumberOfDragons = new JLabel("Number of Dragons");
		lblNumberOfDragons.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumberOfDragons.setBounds(54, 83, 127, 26);
		frame.getContentPane().add(lblNumberOfDragons);

		txtNumberOfDragons = new JTextField();
		txtNumberOfDragons.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNumberOfDragons.setText("1");
		txtNumberOfDragons.setBounds(191, 86, 86, 20);
		frame.getContentPane().add(txtNumberOfDragons);
		txtNumberOfDragons.setColumns(10);

		// Initializes the random number of Dragons checkbox
		randomDragons = new JCheckBox("Random");
		randomDragons.setFont(new Font("Tahoma", Font.PLAIN, 14));
		randomDragons.setBounds(290, 85, 97, 23);
		randomDragons.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent) 
			{
				if(randomDragons.isSelected())
					txtNumberOfDragons.setEnabled(false);
				else
					txtNumberOfDragons.setEnabled(true);
			}
		});
		frame.getContentPane().add(randomDragons);

		// Initializes the label and ComboBox related to the dragon mode
		lblDragonMode = new JLabel("Dragon Mode ");
		lblDragonMode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDragonMode.setBounds(54, 131, 97, 26);
		frame.getContentPane().add(lblDragonMode);

		cbDragonMode = new JComboBox<String>();
		cbDragonMode.setToolTipText("Select the desired dragon behaviour");
		cbDragonMode.setMaximumRowCount(3);
		cbDragonMode.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbDragonMode.setBounds(172, 133, 189, 22);
		cbDragonMode.addItem("No movement");
		cbDragonMode.addItem("Random movement");
		cbDragonMode.addItem("Random movement and sleep");
		frame.getContentPane().add(cbDragonMode);
		
		// Initializes the CheckBox related to the graphical mode
		graphicMazeSel = new JCheckBox("Graphic Maze");
		graphicMazeSel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		graphicMazeSel.setBounds(397, 135, 107, 23);
		frame.getContentPane().add(graphicMazeSel);
		
		// Initializes the CheckBox related to the graphical mode builder
		cbBuilder = new JCheckBox("Builder");
		cbBuilder.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbBuilder.setBounds(290, 37, 97, 23);
		frame.getContentPane().add(cbBuilder);
	}
	
	public void initializeButtons() {
		
		initializeMovementButtons();
		
		btnNewMaze = new JButton("New Maze");
		btnNewMaze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String inputSize = txtMazeSize.getText();
				mazeSize = Integer.parseInt(inputSize);
				graphicMode = graphicMazeSel.isSelected();
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				int maxHeight = dimension.height / 50 - 2;
				
				if(mazeSize <= 5) // dimension must be higher than five
				{
					mazeSize = 7;
					txtMazeSize.setText("7");
				}
				else if(mazeSize % 2 == 0) // dimension must be odd
				{
					mazeSize++;
					Integer mS = mazeSize;
					txtMazeSize.setText(mS.toString());
				}
				
				// since freeSpaces = 7 * mazeSize - 35 and maxDragons = freeSpaces / 7 
				int maxNumberDragons = mazeSize - 5;

				String inputDragons = txtNumberOfDragons.getText();
				numberDragons = Integer.parseInt(inputDragons);
				if(numberDragons < 0 || randomDragons.isSelected())
				{
					numberDragons = 0;	
				}
				else if (numberDragons > maxNumberDragons)
				{
					numberDragons = maxNumberDragons;
					Integer nD = numberDragons;
					txtNumberOfDragons.setText(nD.toString());
				}

				mazeArea.setEditable(true);
				btnUp.setEnabled(true);
				btnLeft.setEnabled(true);
				btnRight.setEnabled(true);
				btnDown.setEnabled(true);

				MazeBuilder mb = new MazeBuilder(mazeSize, numberDragons);
				char [][] matrix = mb.getMaze();
				maze = new Maze(matrix);
				
				if(graphicMode)
				{
					if(mazeSize > maxHeight)
					{
						mazeSize = maxHeight;
						Integer mS = mazeSize;
						txtMazeSize.setText(mS.toString());
						mb = new MazeBuilder(mazeSize, numberDragons);
						matrix = mb.getMaze();
						maze = new Maze(matrix);
					}
					GM = new GraphicMaze(maze, frame.getLocationOnScreen().x, cbBuilder.isSelected());
				}

				status.setText("Move the hero to pick the sword.");

				int mode = cbDragonMode.getSelectedIndex();
				maze.setMode(mode);

				String printable = gi.print(matrix);
				mazeArea.setText(printable);
			}
		});
		btnNewMaze.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewMaze.setBounds(397, 38, 107, 23);
		frame.getContentPane().add(btnNewMaze);

		btnExitGame = new JButton("Exit Game");
		btnExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		btnExitGame.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExitGame.setBounds(397, 85, 107, 23);
		frame.getContentPane().add(btnExitGame);
		
	}
	
	public void initializeMovementButtons() {
		btnUp = new JButton("Up");
		btnUp.setEnabled(false);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				updateMovement(0);
			}
		});
		btnUp.setBounds(395, 254, 70, 23);
		frame.getContentPane().add(btnUp);

		btnLeft = new JButton("Left");
		btnLeft.setEnabled(false);
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				updateMovement(1);
			}
		});
		btnLeft.setBounds(358, 288, 70, 23);
		frame.getContentPane().add(btnLeft);

		btnRight = new JButton("Right");
		btnRight.setEnabled(false);
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				updateMovement(3);
			}
		});
		btnRight.setBounds(439, 288, 70, 23);
		frame.getContentPane().add(btnRight);

		btnDown = new JButton("Down");
		btnDown.setEnabled(false);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				updateMovement(2);
			}
		});
		btnDown.setBounds(395, 322, 70, 23);
		frame.getContentPane().add(btnDown);	
	}
	
	public void updateMovement(int direction) {
		maze.updateGame(direction);
		String printable = gi.print(maze.getMaze());
		mazeArea.setText(printable);

		int dragonsAlive = maze.dragonsAlive();
		
		if(graphicMode)
			GM.updatePanel();
			
		if(maze.getFinished())
		{
			closeInputs();
			if(dragonsAlive == 0)
				status.setText("You won the game");
			else
				status.setText("You were killed!");
		}
		else
		{
			if(dragonsAlive == 1)
				status.setText("Move the hero. " +  dragonsAlive + " dragon to go.");
			else if(dragonsAlive == 0)
				status.setText("Move to the exit. ");
			else
				status.setText("Move the hero. " +  dragonsAlive + " dragons to go.");
		}
	}
}