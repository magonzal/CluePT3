package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import Players.*;



public class Board extends JPanel{
	private int numRows;
	private int numColumns;
	private int numDoors = 0;
	public final static int BOARD_SIZE = 50;
	private BoardCell[][] board;
	private static Map<Character,String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private String charactersFile;
	private String weaponsFile;
	private Set<BoardCell> visited;	
	private LinkedList<Player> players;
	private LinkedList<Player> playableCharacters;
	private LinkedList<ComputerPlayer> computerPlayers;
	private HumanPlayer human;
	private Set<Card> characters;
	private Set<Card> weapons;
	private Set<Card> roomCards;
	private LinkedList<Card> deck;
	private LinkedList<Card> deckBeforeDeal;
	private int numPlayers;
	private Solution solution;
	
	public Board() {
		super();
		board = new BoardCell[BOARD_SIZE][BOARD_SIZE];
		boardConfigFile = "ClueLayout.csv";
		roomConfigFile = "ClueLegend.txt";
		charactersFile = "Characters.txt";
		weaponsFile = "Weapons.txt";
		rooms = new HashMap<Character,String>();
		adjMatrix = new HashMap<BoardCell, LinkedList<BoardCell>>();
		playableCharacters = new LinkedList<Player>();
		solution = new Solution("","","");
	}
	public Board(String boardConfigFile, String roomConfigFile, String charactersFile, String weaponsFile, int numPlayers) {
		super();
		board = new BoardCell[BOARD_SIZE][BOARD_SIZE];
		this.boardConfigFile = boardConfigFile;
		this.roomConfigFile = roomConfigFile;
		this.charactersFile = charactersFile;
		this.weaponsFile = weaponsFile;
		this.numPlayers = numPlayers;
		rooms = new HashMap<Character,String>();
		adjMatrix = new HashMap<BoardCell, LinkedList<BoardCell>>();
	}
	public int getNumDoors() {
		return numDoors;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public static Map<Character, String> getRooms() {
		return rooms;
	}
	public void initialize() {
		try{
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
			loadCharacters();
			loadWeapons();
			createDeck();
			selectAnswer();
			deal();
			createHumanComputer();
		}
		catch (BadConfigFormatException e)
		{
			try {
				PrintWriter fout = new PrintWriter("LogFileInitialize.txt");
				fout.println(e);
				fout.close();
			} catch (FileNotFoundException e1) {
				System.out.println("Cannot create file for writing exception");
			}
		}
		catch (FileNotFoundException e)
		{
			e.getMessage();
		}
	}
	public void loadRoomConfig()  throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = null;
		roomCards = new HashSet<Card>();
		try{
			reader = new FileReader(roomConfigFile);
			Scanner in = new Scanner(reader);
			String dummy;
			String[] ar;

			while (in.hasNext())
			{
				dummy = in.nextLine();
				ar = dummy.split(", ");
				if (!(ar[2].equals("Card") || ar[2].equals("Other")))
				{
					throw new BadConfigFormatException("Bad legend file.");
				}
				rooms.put(ar[0].charAt(0), ar[1]);
				if(ar[2].equals("Card")){
					roomCards.add(new Card(ar[1], CardType.ROOM));
				}
			}
			}
		catch (FileNotFoundException e)
		{
			throw e;
		} 
		catch (BadConfigFormatException e)
		{
			try {
				PrintWriter fout = new PrintWriter("LogFileLoadRoom.txt");
				fout.println(e);
				fout.close();
			} catch (FileNotFoundException e1) {
				System.out.println("Cannot create file for writing exception");
			}
			throw e;
		}
	}
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException{
		FileReader reader = null;
		try {
			reader = new FileReader(boardConfigFile);
			Scanner in = new Scanner(reader);
			String dummy;
			String[] ar;
			numRows = 0;
			Set<Character> checker = rooms.keySet();
			try{
				dummy = in.nextLine();
				ar = dummy.split(",");
				numColumns = ar.length;
				for (int i = 0; i < numColumns; i++)
				{
					if(!checker.contains(ar[i].charAt(0)))
					{	
						throw new BadConfigFormatException("Bad room format");
					}
					if (ar[i].length() == 1)
					{
						board[numRows][i] = new BoardCell(0,i,ar[i].charAt(0),DoorDirection.NONE);
					}
					else
					{
						board[numRows][i] = new BoardCell(0,i,ar[i].charAt(0),ar[i].charAt(1));
					}
				}
			}
			catch (Exception e)
			{	
				throw e;}
			while (in.hasNext())
			{
				numRows++;
				dummy = in.nextLine();
				ar = dummy.split(",");
				if (ar.length != numColumns)
				{
					throw new BadConfigFormatException("Bad Columns file");
				}
				for (int i = 0; i < numColumns; i++)
				{
					if(!checker.contains(ar[i].charAt(0)))
					{
						throw new BadConfigFormatException("Bad room format");
					}
					if (ar[i].length() == 1)
					{
						board[numRows][i] = new BoardCell(numRows,i,ar[i].charAt(0),DoorDirection.NONE);
					}
					else
					{
						board[numRows][i] = new BoardCell(numRows,i,ar[i].charAt(0),ar[i].charAt(1));
					}
				}	
			}
			numRows++;

		}
		catch (FileNotFoundException e) {
			throw e;}
		catch (BadConfigFormatException e){
			try {
				PrintWriter fout = new PrintWriter("LogFileLoadBoard.txt");
				fout.println(e);
				fout.close();
			} catch (FileNotFoundException e1) {
				System.out.println("Cannot create file for writing exception");
			}
			throw e;}
	}
	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}
	public void calcTargets(int row, int col , int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(board[row][col]);
		findAllTargets(board[row][col], pathLength);
	}
	public void calcAdjacencies() {
		Set<Character> checker = rooms.keySet();
		checker.remove('W');
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				LinkedList<BoardCell> holder = new LinkedList<BoardCell>();
				if (checker.contains(board[i][j].getInitial()))
				{
					if(board[i][j].getDoorDirection() != DoorDirection.NONE)
					{
						switch (board[i][j].getDoorDirection()){
						case UP: holder.add(board[i-1][j]);
						break;
						case DOWN: holder.add(board[i+1][j]);
						break;
						case RIGHT: holder.add(board[i][j+1]);
						break;
						case LEFT: holder.add(board[i][j-1]);
						break;
						}}
					adjMatrix.put(board[i][j], holder);
					continue;
				}
				if(i != 0 && !(checker.contains(board[i-1][j].getInitial()) && 
						board[i-1][j].getDoorDirection() != DoorDirection.DOWN))
					holder.add(board[i-1][j]);
				if(j != 0 && !(checker.contains(board[i][j-1].getInitial())
						&& board[i][j-1].getDoorDirection() != DoorDirection.RIGHT))
					holder.add(board[i][j-1]);
				if(i != numRows - 1 && !(checker.contains(board[i+1][j].getInitial()) 
						&& board[i+1][j].getDoorDirection() != DoorDirection.UP))
					holder.add(board[i+1][j]);
				if(j != numColumns - 1 && !(checker.contains(board[i][j+1].getInitial())
						&& board[i][j+1].getDoorDirection() != DoorDirection.LEFT))
					holder.add(board[i][j+1]);
				adjMatrix.put(board[i][j], holder);
			}
		}
	}
	private void findAllTargets(BoardCell thisCell, int numStep)
	{
		LinkedList<BoardCell> adjacentCells = adjMatrix.get(thisCell);
		for(BoardCell cell: adjacentCells)
		{
			if(visited.contains(cell)) continue;
			visited.add(cell);
			if(numStep == 1 || cell.getDoorDirection() != DoorDirection.NONE)
				targets.add(cell);
			else
			{
				findAllTargets(cell, numStep - 1);
			}
			visited.remove(cell);
		}
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	public LinkedList<BoardCell> getAdjList(int row, int col) {
		return adjMatrix.get(board[row][col]);
	}
	//Clue 2 methods
	public void loadWeapons(){
		FileReader reader;
		try{
			reader = new FileReader(weaponsFile);
			Scanner in = new Scanner(reader);
			weapons = new HashSet<Card>();
			String dummy;
			while(in.hasNext()){
				dummy = in.nextLine();
				weapons.add(new Card(dummy, CardType.WEAPON));
			}
			
			
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		
	}
	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined 
		}
		return color;
	}
	public void loadCharacters(){
		FileReader reader;
		try{
			reader = new FileReader(charactersFile);
			Scanner in = new Scanner(reader);
			players = new LinkedList<Player>();
			playableCharacters = new LinkedList<Player>();
			characters = new HashSet<Card>();
			String dummy;
			String[] ar;
			while(in.hasNext()){
				dummy = in.nextLine();
				ar = dummy.split(",");
				players.add(new Player(ar[0].trim(),Integer.parseInt(ar[1].trim()), Integer.parseInt(ar[2].trim()), convertColor(ar[3].trim().toLowerCase())));
				characters.add(new Card(ar[0].trim(), CardType.PERSON));
			}
			for(int i = 0; i < numPlayers; i++){
				playableCharacters.add(players.get(i));
			}
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void selectAnswer(){
		ArrayList<Card> chars = new ArrayList<Card>(characters);
		ArrayList<Card> rm = new ArrayList<Card>(roomCards);
		ArrayList<Card> weap = new ArrayList<Card>(weapons);
		
		Random rand = new Random();
		int index = rand.nextInt(chars.size());
		Card charSolution = (Card) chars.get(index);
		Card roomSolution = (Card) rm.get(index);
		Card weapSolution = (Card) weap.get(index);
		
		deck.remove(charSolution);
		deck.remove(roomSolution);
		deck.remove(weapSolution);
		solution = new Solution(charSolution.getCardName(), roomSolution.getCardName(), weapSolution.getCardName());
		
		
	}
	public void createDeck(){
		deck = new LinkedList<Card>();
		deckBeforeDeal = new LinkedList<Card>();
		deck.addAll(characters);
		deck.addAll(roomCards);
		deck.addAll(weapons);
		Collections.shuffle(deck);
		for(Card c: deck){
			deckBeforeDeal.add(c);
		}
	}

	public void deal(){
		for(int i = 0; i < deck.size(); i++){
			players.get(i % players.size()).setHand(deck.get(i));
		}
	}
	
	public Card handleSuggestion(Solution suggestion, String accusingPlayer, BoardCell clicked){
		int start = -1;
		for(int i = 0; i < playableCharacters.size();i++){
			if(playableCharacters.get(i).getName().equals(accusingPlayer) )
				start = i;
		}

		Card card;
		for(int i = start + 1; i<playableCharacters.size();i++){
			card = playableCharacters.get(i).disproveSuggestion(suggestion);
			if(card == null){
				continue;
			}
			else{
				return card;
			}
		}
		
		for(int i = 0; i< start;i++){
			card = playableCharacters.get(i).disproveSuggestion(suggestion);
			if(card == null){
				continue;
			}
			else{
				return card;
			}
		}
		return null;
	
	}
	public boolean checkAccusation(Solution accusation){
		return solution.equals(accusation);
	}
	
	public LinkedList<Player> getPlayers(){
		return players;
	}
	
	public LinkedList<Card> getDeckBeforeDeal(){
		return deckBeforeDeal;
	}
	
	public int getNumberPersons(){
		return players.size();
	}
	
	public int getNumberWeapons(){
		return weapons.size();
	}
	
	public int getNumberRooms(){
		return roomCards.size();
	}
	
	public int getDeckSizeBeforeDeal(){
		return deckBeforeDeal.size();
	}
	public int getDealtCardSize(){
		int total = 0;
		for(Player p: playableCharacters){
			total= total + p.getHand().size();
		}
		return total;
	}
	
	public LinkedList<Player> getPlayable(){
		return playableCharacters;
	}
	
	public Solution getSolution(){
		return solution;
	}
	public void setSolution(Solution sol){
		solution.setPerson(sol.person);
		solution.setRoom(sol.room);
		solution.setWeapon(sol.weapon);

	}
	
	public void addPlayableCharacters(Player player){
		playableCharacters.add(player);
	}
	
	public void clearPlayers(){
		playableCharacters.clear();
	}
	
	public Set<Card> getWeapons(){
		return weapons;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		HashMap<Character,Integer> xcoord = new HashMap<Character,Integer>();
		HashMap<Character, Integer> ycoord = new HashMap<Character, Integer>();
		for(int i = 0; i != 25; i++){
			for(int j=0; j!= 25;j++){
				board[j][i].draw(g, i*20, j*20, 20,20);
				if(board[i][j].isRoom()){
					xcoord.put(board[j][i].getInitial(),j);
					ycoord.put(board[j][i].getInitial(),i);
				}
			}
		}
		
		for(Player p: players){
			p.draw(g, p.getColumn()*20, p.getRow()*20, 20, 20);
		}
		for(char c: rooms.keySet()){
			g.setColor(Color.BLUE);
			g.drawString(rooms.get(c), ycoord.get(c)*16, xcoord.get(c)*18);
		}
		
	}
	
	public void createHumanComputer(){
		computerPlayers = new LinkedList<ComputerPlayer>();
		human = new HumanPlayer();
		for(Player p: players){
			if(p.getName().equals("Miss Scarlett")){
				human = new HumanPlayer(p.getName(), p.getRow(), p.getColumn(), p.getColor());
				human.setTotalHand(p.getHand());	
				}
			else{
				ComputerPlayer cp= new ComputerPlayer(p.getName(), p.getRow(), p.getColumn(), p.getColor());
				cp.setTotalHand(p.getHand());
				computerPlayers.add(cp);
			}
			}
		}
		
		
}
