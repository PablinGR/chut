package com.bean;

import MD.Campeonatos;
import com.session.CampeonatosFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "campeonatosController")
@ViewScoped
public class CampeonatosController extends AbstractController<Campeonatos> {

    @Inject
    private ReservasController idreservaController;
    @Inject
    private PatrocinadoresController rucpatrocinadorController;

    // Flags to indicate if child collections are empty
    private boolean isArbitrosCollectionEmpty;

    public CampeonatosController() {
        // Inform the Abstract parent controller of the concrete Campeonatos Entity
        super(Campeonatos.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idreservaController.setSelected(null);
        rucpatrocinadorController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsArbitrosCollectionEmpty();
    }

    /**
     * Sets the "selected" attribute of the Reservas controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdreserva(ActionEvent event) {
        Campeonatos selected = this.getSelected();
        if (selected != null && idreservaController.getSelected() == null) {
            idreservaController.setSelected(selected.getIdreserva());
        }
    }

    /**
     * Sets the "selected" attribute of the Patrocinadores controller in order
     * to display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareRucpatrocinador(ActionEvent event) {
        Campeonatos selected = this.getSelected();
        if (selected != null && rucpatrocinadorController.getSelected() == null) {
            rucpatrocinadorController.setSelected(selected.getRucpatrocinador());
        }
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
        return "/app/arbitros/index";
    }

}
