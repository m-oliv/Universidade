public interface List<T> extends Iterable<T>{
  public boolean empty();
  public boolean contains(T x);
  public int size();
  public T get(int i) throws NoInvalidoException;
  public T remove(int i) throws NoInvalidoException;
  public T remove(T x) throws NoInvalidoException;
  public void add(T x) throws NoInvalidoException;
  public void add(T x, int i) throws NoInvalidoException;
}