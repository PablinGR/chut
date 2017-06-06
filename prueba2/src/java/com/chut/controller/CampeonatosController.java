package com.chut.controller;

import com.chut.controller.util.MobilePageController;
import com.chut.entidades.Campeonatos;
import com.chut.facade.CampeonatosFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "campeonatosController")
@ViewScoped
public class CampeonatosController extends AbstractController<Campeonatos> {

    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isPatrocinadoresCollectionEmpty;
    private boolean isReservasCollectionEmpty;
    private boolean isArbitrosCollectionEmpty;

    public CampeonatosController() {
        // Inform the Abstract parent controller of the concrete Campeonatos Entity
        super(Campeonatos.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsPatrocinadoresCollectionEmpty();
        this.setIsReservasCollectionEmpty();
        this.setIsArbitrosCollectionEmpty();
    }

    public boolean getIsPatrocinadoresCollectionEmpty() {
        return this.isPatrocinadoresCollectionEmpty;
    }

    private void setIsPatrocinadoresCollectionEmpty() {
        Campeonatos selected = this.getSelected();
        if (selected != null) {
            CampeonatosFacade ejbFacade = (CampeonatosFacade) this.getFacade();
            this.isPatrocinadoresCollectionEmpty = ejbFacade.isPatrocinadoresCollectionEmpty(selected);
        } else {
            this.isPatrocinadoresCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Patrocinadores entities
     * that are retrieved from Campeonatos?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Patrocinadores page
     */
    public String navigatePatrocinadoresCollection() {
        Campeonatos selected = this.getSelected();
        if (selected != null) {
            CampeonatosFacade ejbFacade = (CampeonatosFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Patrocinadores_items", ejbFacade.findPatrocinadoresCollection(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/patrocinadores/index";
    }

    public boolean getIsReservasCollectionEmpty() {
        return this.isReservasCollectionEmpty;
    }

    private void setIsReservasCollectionEmpty() {
        Campeonatos selected = this.getSelected();
        if (selected != null) {
            CampeonatosFacade ejbFacade = (CampeonatosFacade) this.getFacade();
            this.isReservasCollectionEmpty = ejbFacade.isReservasCollectionEmpty(selected);
        } else {
            this.isReservasCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Reservas entities that
     * are retrieved from Campeonatos?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Reservas page
     */
    public String navigateReservasCollection() {
        Campeonatos selected = this.getSelected();
        if (selected != null) {
            CampeonatosFacade ejbFacade = (CampeonatosFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Reservas_items", ejbFacade.findReservasCollection(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/reservas/index";
    }

    public boolean getIsArbitrosCollectionEmpty() {
        return this.isArbitrosCollectionEmpty;
    }

    private void setIsArbitrosCollectionEmpty() {
        Campeonatos selected = this.getSelected();
        if (selected != null) {
            CampeonatosFacade ejbFacade = (CampeonatosFacade) this.getFacade();
            this.isArbitrosCollectionEmpty = ejbFacade.isArbitrosCollectionEmpty(selected);
        } else {
            this.isArbitrosCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Arbitros entities that
     * are retrieved from Campeonatos?cap_first and returns the navigation
     * outcome.
     *
     * @return navigation outcome for Arbitros page
     */
    public String navigateArbitrosCollection() {
        Campeonatos selected = this.getSelected();
        if (selected != null) {
            CampeonatosFacade ejbFacade = (CampeonatosFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Arbitros_items", ejbFacade.findArbitrosCollection(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/arbitros/index";
    }

}
