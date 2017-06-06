package com.chut.controller;

import com.chut.controller.util.MobilePageController;
import com.chut.entidades.Partidos;
import com.chut.facade.PartidosFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "partidosController")
@ViewScoped
public class PartidosController extends AbstractController<Partidos> {

    @Inject
    private CanchasController idcanchaController;
    @Inject
    private EquiposController idequipoController;
    @Inject
    private MobilePageController mobilePageController;

    // Flags to indicate if child collections are empty
    private boolean isReservasCollectionEmpty;

    public PartidosController() {
        // Inform the Abstract parent controller of the concrete Partidos Entity
        super(Partidos.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idcanchaController.setSelected(null);
        idequipoController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsReservasCollectionEmpty();
    }

    /**
     * Sets the "selected" attribute of the Canchas controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdcancha(ActionEvent event) {
        Partidos selected = this.getSelected();
        if (selected != null && idcanchaController.getSelected() == null) {
            idcanchaController.setSelected(selected.getIdcancha());
        }
    }

    /**
     * Sets the "selected" attribute of the Equipos controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdequipo(ActionEvent event) {
        Partidos selected = this.getSelected();
        if (selected != null && idequipoController.getSelected() == null) {
            idequipoController.setSelected(selected.getIdequipo());
        }
    }

    public boolean getIsReservasCollectionEmpty() {
        return this.isReservasCollectionEmpty;
    }

    private void setIsReservasCollectionEmpty() {
        Partidos selected = this.getSelected();
        if (selected != null) {
            PartidosFacade ejbFacade = (PartidosFacade) this.getFacade();
            this.isReservasCollectionEmpty = ejbFacade.isReservasCollectionEmpty(selected);
        } else {
            this.isReservasCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Reservas entities that
     * are retrieved from Partidos?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Reservas page
     */
    public String navigateReservasCollection() {
        Partidos selected = this.getSelected();
        if (selected != null) {
            PartidosFacade ejbFacade = (PartidosFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Reservas_items", ejbFacade.findReservasCollection(selected));
        }
        return this.mobilePageController.getMobilePagesPrefix() + "/app/reservas/index";
    }

}
