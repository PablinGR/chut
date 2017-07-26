package com.bean;

import MD.Canchas;
import com.session.CanchasFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "canchasController")
@ViewScoped
public class CanchasController extends AbstractController<Canchas> {

    // Flags to indicate if child collections are empty
    private boolean isPropietariosCollectionEmpty;
    private boolean isPartidosCollectionEmpty;

    public CanchasController() {
        // Inform the Abstract parent controller of the concrete Canchas Entity
        super(Canchas.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsPropietariosCollectionEmpty();
        this.setIsPartidosCollectionEmpty();
    }

    public boolean getIsPropietariosCollectionEmpty() {
        return this.isPropietariosCollectionEmpty;
    }

    private void setIsPropietariosCollectionEmpty() {
        Canchas selected = this.getSelected();
        if (selected != null) {
            CanchasFacade ejbFacade = (CanchasFacade) this.getFacade();
            this.isPropietariosCollectionEmpty = ejbFacade.isPropietariosCollectionEmpty(selected);
        } else {
            this.isPropietariosCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Propietarios entities
     * that are retrieved from Canchas?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Propietarios page
     */
    public String navigatePropietariosCollection() {
        Canchas selected = this.getSelected();
        if (selected != null) {
            CanchasFacade ejbFacade = (CanchasFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Propietarios_items", ejbFacade.findPropietariosCollection(selected));
        }
        return "/app/propietarios/index";
    }

    public boolean getIsPartidosCollectionEmpty() {
        return this.isPartidosCollectionEmpty;
    }

    private void setIsPartidosCollectionEmpty() {
        Canchas selected = this.getSelected();
        if (selected != null) {
            CanchasFacade ejbFacade = (CanchasFacade) this.getFacade();
            this.isPartidosCollectionEmpty = ejbFacade.isPartidosCollectionEmpty(selected);
        } else {
            this.isPartidosCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Partidos entities that
     * are retrieved from Canchas?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Partidos page
     */
    public String navigatePartidosCollection() {
        Canchas selected = this.getSelected();
        if (selected != null) {
            CanchasFacade ejbFacade = (CanchasFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Partidos_items", ejbFacade.findPartidosCollection(selected));
        }
        return "/app/partidos/index";
    }

}
