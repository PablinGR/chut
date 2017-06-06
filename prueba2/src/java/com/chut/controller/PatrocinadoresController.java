package com.chut.controller;

import com.chut.controller.util.MobilePageController;
import com.chut.entidades.Patrocinadores;
import com.chut.facade.PatrocinadoresFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "patrocinadoresController")
@ViewScoped
public class PatrocinadoresController extends AbstractController<Patrocinadores> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isCampeonatosCollectionEmpty;

    public PatrocinadoresController() {
        // Inform the Abstract parent controller of the concrete Patrocinadores Entity
        super(Patrocinadores.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsCampeonatosCollectionEmpty();
    }

    public boolean getIsCampeonatosCollectionEmpty() {
        return this.isCampeonatosCollectionEmpty;
    }

    private void setIsCampeonatosCollectionEmpty() {
        Patrocinadores selected = this.getSelected();
        if (selected != null) {
            PatrocinadoresFacade ejbFacade = (PatrocinadoresFacade) this.getFacade();
            this.isCampeonatosCollectionEmpty = ejbFacade.isCampeonatosCollectionEmpty(selected);
        } else {
            this.isCampeonatosCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Campeonatos entities that
     * are retrieved from Patrocinadores?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Campeonatos page
     */
    public String navigateCampeonatosCollection() {
        Patrocinadores selected = this.getSelected();
        if (selected != null) {
            PatrocinadoresFacade ejbFacade = (PatrocinadoresFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Campeonatos_items", ejbFacade.findCampeonatosCollection(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/campeonatos/index";
    }

}
