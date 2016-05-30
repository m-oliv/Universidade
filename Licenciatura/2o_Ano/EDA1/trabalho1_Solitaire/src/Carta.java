
  public class Carta {
    public enum Valor { AS, DOIS, TRES, QUATRO, CINCO, SEIS,
        SETE, OITO, NOVE, DEZ, VALETE, DAMA, REI }

    public enum Naipe { ESPADAS, OUROS, COPAS, PAUS }

    private final Valor o_valor;
    private final Naipe o_naipe;
    
    public Carta(){
      o_valor=Valor.AS;
      o_naipe=Naipe.COPAS;
    }
    public Carta(Valor v, Naipe n) {
        this.o_valor = v;
        this.o_naipe = n;
    }

    public Valor valor() { return o_valor; }
    public Naipe naipe() { return o_naipe; }
    public boolean equals(Object x){
      if(this==x)
        return true;
      if(x==null ||x.getClass()!=this.getClass())
        return false;
      Carta c=(Carta) x;
      return c.valor().equals(this.valor()) && c.naipe().equals(this.naipe());
    }
    
    public String toString() { return o_valor+ " de " + o_naipe; }

    public static void main(String[]args){
    	Carta c = new Carta();
    	System.out.println(c.valor());
    	System.out.println(c.naipe());
    	for(int i = 0; i<13;i++){
    	System.out.println("Valor index "+i+": "+Valor.values()[i]);
    	}
    }


}