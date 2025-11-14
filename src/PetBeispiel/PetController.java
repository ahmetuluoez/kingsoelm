package PetBeispiel;

import javafx.event.ActionEvent;
import PetBeispiel.Pet;
import PetBeispiel.Pet.Species;
import PetBeispiel.Pet.Gender;



public class PetController {
	private PetView view;
	private PetModel model;

	public PetController(PetModel model, PetView view) {
		this.model = model;
		this.view = view;
		
		view.btnSave.setOnAction(this::save);
		view.btnDelete.setOnAction(this::delete);
		
		view.btnDelete.disableProperty().bind(model.petProperty().isNull());
		view.btnSave.disableProperty().bind(view.txtName.textProperty().isEmpty());
	}
	
	private void save(ActionEvent e) {
		Species species = view.cmbSpecies.getValue();
		Gender gender = view.cmbGender.getValue();
		String name = view.txtName.getText();
		if (species != null && gender != null && name != null && name.length() > 0) {
			model.savePet(species, gender, name);
			updateView(model.getPet());
		}
	}
	
	private void delete(ActionEvent e) {
		model.deletePet();
		updateView(model.getPet());
	}
	
	private void updateView(Pet pet) {
		if (pet != null) {
			view.lblDataID.setText(Integer.toString(pet.getID()));
			view.lblDataName.setText(pet.getName());
			view.lblDataSpecies.setText(pet.getSpecies().toString());
			view.lblDataGender.setText(pet.getGender().toString());
		} else {
			view.lblDataID.setText("");
			view.lblDataName.setText("");
			view.lblDataSpecies.setText("");
			view.lblDataGender.setText("");
		}
	}
}
