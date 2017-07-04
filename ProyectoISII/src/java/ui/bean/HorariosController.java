package ui.bean;

import MD.Horarios;
import com.facade.HorariosFacade;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "horariosController")
@ViewScoped
public class HorariosController extends AbstractController<Horarios> {

    // Flags to indicate if child collections are empty
    private boolean isReservasCollectionEmpty;

    public HorariosController() {
        // Inform the Abstract parent controller of the concrete Horarios Entity
        super(Horarios.class);
    }

    /**
     * Set the "is[ChildCollection]Empty" property for OneToMany fields.
     */
    @Override
    protected void setChildrenEmptyFlags() {
        this.setIsReservasCollectionEmpty();
    }

    public boolean getIsReservasCollectionEmpty() {
        return this.isReservasCollectionEmpty;
    }

    private void setIsReservasCollectionEmpty() {
        Horarios selected = this.getSelected();
        if (selected != null) {
            HorariosFacade ejbFacade = (HorariosFacade) this.getFacade();
            this.isReservasCollectionEmpty = ejbFacade.isReservasCollectionEmpty(selected);
        } else {
            this.isReservasCollectionEmpty = true;
        }
    }

    /**
     * Sets the "items" attribute with a collection of Reservas entities that
     * are retrieved from Horarios?cap_first and returns the navigation outcome.
     *
     * @return navigation outcome for Reservas page
     */
    public String navigateReservasCollection() {
        Horarios selected = this.getSelected();
        if (selected != null) {
            HorariosFacade ejbFacade = (HorariosFacade) this.getFacade();
            FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("Reservas_items", ejbFacade.findReservasCollection(selected));
        }
        return "/app/reservas/index";
    }

}
