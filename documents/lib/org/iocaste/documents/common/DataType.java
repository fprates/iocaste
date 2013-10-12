/*
    DataType.java, tipos de dado.
    Copyright (C) 2011  Francisco de Assis Prates
   
    Este programa é software livre; você pode redistribuí-lo e/ou
    modificá-lo sob os termos da Licença Pública Geral GNU, conforme
    publicada pela Free Software Foundation; tanto a versão 2 da
    Licença como (a seu critério) qualquer versão mais nova.

    Este programa é distribuído na expectativa de ser útil, mas SEM
    QUALQUER GARANTIA; sem mesmo a garantia implícita de
    COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
    PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
    detalhes.
 
    Você deve ter recebido uma cópia da Licença Pública Geral GNU
    junto com este programa; se não, escreva para a Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
    02111-1307, USA.
 */

package org.iocaste.documents.common;

/**
 * Tipos de dados.
 * 
 * @author Francisco Prates
 *
 */
public class DataType {
    public static final int CHAR = 0;
    public static final int DATE = 1;
    public static final int DEC = 2;
    public static final int NUMC = 3;
    public static final int TIME = 4;
    public static final int BOOLEAN = 5;
    public static final int BYTE = 6;
    public static final int INT = 7;
    public static final int LONG = 8;
    public static final int SHORT = 9;
    
    public static final boolean UPPERCASE = true;
    public static final boolean KEEPCASE = false;
    public static final byte MAX_INT_LEN = 10;
}
