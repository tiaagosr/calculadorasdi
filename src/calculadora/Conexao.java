/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

/**
 *
 * @author udesc
 */
public abstract class Conexao implements Runnable{
    protected abstract void recebePacote();
}
