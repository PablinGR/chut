package ui.bean;

import MD.Partidos;
import com.facade.PartidosFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "partidosController")
@ViewScoped
public class PartidosController extends AbstractController<Partidos> {

    @Inject
    private EquiposController idequipoController;
    @Inject
    private ReservasController idreservaController;
    @Inject
    private CanchasController idcanchaController;

    // Flags to indicate if child collections are empty
    private boolean isCanchasCollectionEmpty;
    private boolean isReservasCollectionEmpty;

    public PartidosController() {
        // Inform the Abstract parent controller of the concrete Partidos Entity
        super(Partidos.class);
    }

    /**
     * Resets the "selected" attribute of any parent Entity controllers.
     */
    public void resetParents() {
        idequipoController.setSelected(null);
        idreservaController.setSelected(null);
        idcanchaController.setSelected(null);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsCanchasCollectionEmpty();
        this.setIsReservasCollectionEmpty();
    }

    public boolean getIsCanchasCollectionEmpty() {
        return this.isCanchasCollectionEmpty;
    }

    private void setIsCanchasCollectionEmpty() {
        Partidos selected = this.getSelected();
        if (selected != null) {
            PartidosFacade ejbFacade = (PartidosFacade) this.getFacade();
            this.isCanchasCollectionEmpty = ejbFacade.isCanchasCollectionEmpty(selected);
        } else {
            this.isCanchasCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Canchas entities that are
     * retrieved from Partidos?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Canchas page
     */
    public String navigateCanchasCollection() {
        Partidos selected = this.getSelected();
        if (selected != null) {
            PartidosFacade ejbFacade = (PartidosFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Canchas_items", ejbFacade.findCanchasCollection(selected));
        }
        return "/app/canchas/index";
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

    /**
     * Sets the "selected" attribute of the Reservas controller in order to
     * display its data in its View dialog.
     *
     * @param event Event object for the widget that triggered an action
     */
    public void prepareIdreserva(ActionEvent event) {
        Partidos selected = this.getSelected();
        if (selected != null && idreservaController.getSelected() == null) {
            idreservaController.setSelected(selected.getIdreserva());
        }
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
        return "/app/reservas/index";
    }

}
