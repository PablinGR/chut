package ui.bean;

import MD.Campeonatos;
import com.facade.CampeonatosFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "campeonatosController")
@ViewScoped
public class CampeonatosController extends AbstractController<Campeonatos> {

    @Inject
    private PatrocinadoresController rucpatrocinadorController;

    // Flags to indicate if child collections are empty
    private boolean isReservasCollectionEmpty;
    private boolean isArbitrosCollectionEmpty;

    public CampeonatosController() {
        // Inform the Abstract parent controller of the concrete Campeonatos Entity
        super(Campeonatos.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        rucpatrocinadorController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsReservasCollectionEmpty();
        this.setIsArbitrosCollectionEmpty();
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
        return "/app/reservas/index";
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
