package export;


import com.curso.spring.rest.model.entity.Cliente;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase envoltoria para un listado de clientes, se usará para paginar y exportar a xml
 */
@XmlRootElement(name = "clientes")
public class ClienteList implements Serializable {

    private static final long serialVersionUID = 4156636342092265197L;

    @XmlElement(name = "cliente")
    public List<Cliente> clientes;

    public ClienteList() {
        clientes = new ArrayList<>();
    }

    public ClienteList(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
