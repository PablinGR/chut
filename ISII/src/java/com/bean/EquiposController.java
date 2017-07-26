package com.bean;

import MD.Equipos;
import com.session.EquiposFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "equiposController")
@ViewScoped
public class EquiposController extends AbstractController<Equipos> {

    @Inject
    private JugadoresController cedulajugadorController;

    // Flags to indicate if child collections are empty
    private boolean isPartidosCollectionEmpty;

    public EquiposController() {
        // Inform the Abstract parent controller of the concrete Equipos Entity
        super(Equipos.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        cedulajugadorController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsPartidosCollectionEmpty();
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

    /**
     * Sets the "selected" attribute of the Jugadores controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareCedulajugador(ActionEvent event) {
        Equipos selected = this.getSelected();
        if (selected != null && cedulajugadorController.getSelected() == null) {
            cedulajugadorController.setSelected(selected.getCedulajugador());
        }
    }

}
