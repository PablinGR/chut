package ui.bean;

import MD.Jugadores;
import com.facade.JugadoresFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "jugadoresController")
@ViewScoped
public class JugadoresController extends AbstractController<Jugadores> {

    // Flags to indicate if child collections are empty
    private boolean isEquiposCollectionEmpty;

    public JugadoresController() {
        // Inform the Abstract parent controller of the concrete Jugadores Entity
        super(Jugadores.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsEquiposCollectionEmpty();
    }

    public boolean getIsEquiposCollectionEmpty() {
        return this.isEquiposCollectionEmpty;
    }

    private void setIsEquiposCollectionEmpty() {
        Jugadores selected = this.getSelected();
        if (selected != null) {
            JugadoresFacade ejbFacade = (JugadoresFacade) this.getFacade();
            this.isEquiposCollectionEmpty = ejbFacade.isEquiposCollectionEmpty(selected);
        } else {
            this.isEquiposCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Equipos entities that are
     * retrieved from Jugadores?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Equipos page
     */
    public String navigateEquiposCollection() {
        Jugadores selected = this.getSelected();
        if (selected != null) {
            JugadoresFacade ejbFacade = (JugadoresFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Equipos_items", ejbFacade.findEquiposCollection(selected));
        }
        return "/app/equipos/index";
    }

}
