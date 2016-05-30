public class Solitaire{
  // variaveis de instancia: varias zonas do jogo
	private ZonaA za;
	private ZonaB zb;
	private ZonaC zc;
	private  Baralho b;
  public Solitaire() throws NoInvalidoException, OverflowException{
    //criar um baralho e distribuir as cartas pelas diferentes zonas
    
	  this.b = new Baralho(); // cria um baralho de 52 cartas
    
	  DoubleLinkedList<Carta> zatemp = new DoubleLinkedList<Carta>(); // lista temporaria de cartas da zona A
	  DoubleLinkedList<Carta> zctemp = new DoubleLinkedList<Carta>(); // lista temporaria de cartas da zona C
	 
	  for(int i = 0; i<24;i++){ // coloca 24 cartas na lista temporaria da zona A
		  zatemp.add(b.get());
	  }
	  
	  za = new ZonaA(zatemp); // inicializa a zona A
	  
	  zb = new ZonaB(); // inicializa a zona B
	
	  for(int j = 0; j<28;j++){ // coloca 28 cartas na lista temporaria da zona C
		  zctemp.add(b.get());
	  }
	  
	  zc = new ZonaC(zctemp); // inicializa a zona C
   
  }
  
  public void virar() throws OverflowException, EmptyException, InvalidPlayException{
    //virar uma carta (Zona A)
	  za.virar();
  }
  
  public Carta retiraZonaA() throws EmptyException, OverflowException, InvalidPlayException{
	//retira uma carta da zona  A
	return za.retirar();
    
  }
  
  public void addZonaB(Carta x) throws OverflowException, EmptyException, InvalidPlayException{
    //adiciona a Carta x a zona B
	  zb.put(x);
  }
  
  public List<Carta> retiraZonaC(int i, int n) throws InvalidPlayException, EmptyException, OverflowException{
    //retira n Cartas da i-esima pilha da zona C, e retorna-as numa lista de cartas
    return zc.retira(n, i);
  }
  
   public Carta retiraZonaC(int i) throws EmptyException, OverflowException,InvalidPlayException{
    //retira 1a Carta da i-esima pilha da zona C, e retorna-a
    return zc.retira(i);
  }
  public void addZonaC(int i, List<Carta> l) throws NoInvalidoException, InvalidPlayException, EmptyException, OverflowException{
    //adicinar a lista de cartas a i-esima lista de cartas da zona C
	  zc.add(l, i);
  }
  public void addZonaC(int i, Carta x) throws InvalidPlayException, EmptyException, OverflowException{
    //adicinar a  cartas x a i-esima lista de cartas da zona C
	  zc.add(i, x);
	  
  }
  
  public static void main(String[] args) throws OverflowException, EmptyException, InvalidPlayException, NoInvalidoException{
    Solitaire s =new Solitaire();
    
    // ------------------------------ TESTES ZONA A:
    s.virar(); 
    System.out.println("Carta retirada <zona A>: "+s.retiraZonaA()); 
    
    
    
    
    // ------------------------------ TESTES ZONA B:
    //s.addZonaB(new Carta()); // teste - colocar um AS numa das pilhas da zona B.
    //s.addZonaB(new Carta(Carta.Valor.DOIS,Carta.Naipe.COPAS)); // teste - colocar uma carta do naipe certo e na ordem certa numa pilha.
    
    //s.addZonaB(new Carta(Carta.Valor.TRES,Carta.Naipe.COPAS)); // teste - colocar uma carta do naipe certo e na ordem errada numa pilha (testa excepcao).
    //s.addZonaB(new Carta(Carta.Valor.DOIS,Carta.Naipe.OUROS)); // teste - colocar uma carta que nao e um AS numa das pilhas da zona B (testa excepcao).
    
    
    
    
    // ------------------------------ TESTES ZONA C:
    
    //************************* teste retiraZonaC(int i, int n)
    
    //DoubleLinkedList<Carta> rem = (DoubleLinkedList<Carta>) s.retiraZonaC(3, 3); // teste - retirar 3 cartas da pilha 3
    //System.out.println("lista de cartas removidas da pilha 3 <zona c>: "+rem.toString());
    
  //************************* teste retiraZonaC(int i, Carta x)
    
    //Carta x = s.retiraZonaC(2); // teste - retirar uma carta da pilha 2 da zona C.
    //System.out.println("carta removida da pilha 2 <zona c>:"+ x);
    
    
    /********************* Teste addZonaC(int i, List<Carta> l) ********************************/
    /*
    s.retiraZonaC(1,1); // remove todas as cartas da pilha 1.
    DoubleLinkedList<Carta> ins = new DoubleLinkedList<Carta>(); // lista com 3 cartas para inserir (esta lista serve apenas para testar o funcionamento dos metodos da zona C).
    ins.add(new Carta(Carta.Valor.REI,Carta.Naipe.ESPADAS));
    ins.add(new Carta(Carta.Valor.DAMA,Carta.Naipe.COPAS));
    ins.add(new Carta(Carta.Valor.VALETE,Carta.Naipe.PAUS));
    
    s.addZonaC(1, ins);
    */
    
    //************************* teste addZonaC(int i, Carta x)
    
    //s.retiraZonaC(1,1); // remove todas as cartas da pilha 1.
    //s.addZonaC(1, new Carta(Carta.Valor.REI,Carta.Naipe.ESPADAS)); // teste - caso em que a carta a inserir na pilha e um rei.
    //s.addZonaC(1, new Carta(Carta.Valor.AS,Carta.Naipe.ESPADAS)); // teste - caso em que a carta a inserir na pilha nao e um rei.
  }
  
  
}