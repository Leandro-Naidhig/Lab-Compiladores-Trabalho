/**
    Integrantes:    Leandro Naidhig 726555
                    Gustavo Buoro Branco de Souza 726533
                    
 */
package ast;

import java.util.ArrayList;

/** This class represents a metaobject annotation as <code>{@literal @}ce(...)</code> in <br>
 * <code>
 * @ce(5, "'class' expected") <br>
 * clas Program <br>
 *     public void run() { } <br>
 * end <br>
 * </code>
 *
   @author Jos�

 */

public class ClassDec {

    public ClassDec(String name, MemberList memberList) {
        this.name = name;
        this.memberList = memberList;   
    }

    public String getName() {
		return name;
	}

    private String name;
    private MemberList memberList;

}