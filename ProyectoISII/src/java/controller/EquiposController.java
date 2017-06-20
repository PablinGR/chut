package controller;

import MD.Equipos;
import facade.EquiposFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "equiposController")
@ViewScoped
public class EquiposController extends AbstractController<Equipos> {

    // Flags to indicate if child collections are empty
    private boolean isJugadoresCollectionEmpty;
    private boolean isPartidosCollectionEmpty;

    public EquiposController() {
        // Inform the Abstract parent controller of the concrete Equipos Entity
        super(Equipos.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsJugadoresCollectionEmpty();
        this.setIsPartidosCollectionEmpty();
    }

    public boolean getIsJugadoresCollectionEmpty() {
        return this.isJugadoresCollectionEmpty;
    }

    private void setIsJugadoresCollectionEmpty() {
        Equipos selected = this.getSelected();
        if (selected != null) {
            EquiposFacade ejbFacade = (EquiposFacade) this.getFacade();
            this.isJugadoresCollectionEmpty = ejbFacade.isJugadoresCollectionEmpty(selected);
        } else {
            this.isJugadoresCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Jugadores entities that
     * are retrieved from Equipos?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Jugadores page
     */
    public String navigateJugadoresCollection() {
        Equipos selected = this.getSelected();
        if (selected != null) {
            EquiposFacade ejbFacade = (EquiposFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Jugadores_items", ejbFacade.findJugadoresCollection(selected));
        }
        return "/app/jugadores/index";
    }

    public boolean getIsPartidosCollectionEmpty() {
        return this.isPartidosCollectionEmpty;
    }

    private void setIsPartidosCollectionEmpty() {
        Equipos selected = this.getSelected();
        if (selected != null) {
            EquiposFacade ejbFacade = (EquiposFacade) this.getFacade();
            this.isPartidosCollectionEmpty = ejbFacade.isPartidosCollectionEmpty(selected);
        } else {
            this.isPartidosCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Partidos entities that
     * are retrieved from Equipos?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Partidos page
     */
    public String navigatePartidosCollection() {
        Equipos selected = this.getSelected();
        if (selected != null) {
            EquiposFacade ejbFacade = (EquiposFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Partidos_items", ejbFacade.findPartidosCollection(selected));
        }
        return "/app/partidos/index";
    }

}
