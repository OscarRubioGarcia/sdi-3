package com.sdi.presentation;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.model.Task;

@ManagedBean(name="tarea")
@SessionScoped
public class BeanTarea extends Task implements Serializable {
	
	private static final long serialVersionUID = 55557L;
	
	public BeanTarea() {
		iniciaTarea(null);
	}
	
	public void setTarea(Task task) {
		setId(task.getId());
		setCategoryId(task.getCategoryId());
		setComments(task.getComments());
		setCreated(task.getCreated());
		setFinished(task.getFinished());
		setPlanned(task.getPlanned());
		setTitle(task.getTitle());
		setUserId(task.getUserId());
	}
	
	public void iniciaTarea(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle =
		facesContext.getApplication().getResourceBundle(facesContext, "msgs");
		setId(null);
		setCategoryId(null);
		setComments(bundle.getString("valorDefectoComments"));
		setCreated(new Date());
		setFinished(new Date());
		setPlanned(new Date());
		setTitle(bundle.getString("valorDefectoTittle"));
		setUserId(null);
	}

}
