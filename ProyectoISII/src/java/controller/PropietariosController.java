package controller;

import MD.Propietarios;
import facade.PropietariosFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "propietariosController")
@ViewScoped
public class PropietariosController extends AbstractController<Propietarios> {

    // Flags to indicate if child collections are empty
    private boolean isCanchasCollectionEmpty;

    public PropietariosController() {
        // Inform the Abstract parent controller of the concrete Propietarios Entity
        super(Propietarios.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsCanchasCollectionEmpty();
    }

    public boolean getIsCanchasCollectionEmpty() {
        return this.isCanchasCollectionEmpty;
    }

    private void setIsCanchasCollectionEmpty() {
        Propietarios selected = this.getSelected();
        if (selected != null) {
            PropietariosFacade ejbFacade = (PropietariosFacade) this.getFacade();
            this.isCanchasCollectionEmpty = ejbFacade.isCanchasCollectionEmpty(selected);
        } else {
            this.isCanchasCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Canchas entities that are
     * retrieved from Propietarios?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Canchas page
     */
    public String navigateCanchasCollection() {
        Propietarios selected = this.getSelected();
        if (selected != null) {
            PropietariosFacade ejbFacade = (PropietariosFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Canchas_items", ejbFacade.findCanchasCollection(selected));
        }
        return "/app/canchas/index";
    }

}
