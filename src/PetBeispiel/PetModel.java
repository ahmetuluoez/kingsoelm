 package PetBeispiel;

import javafx.beans.property.SimpleObjectProperty;
import PetBeispiel.Pet;

public class PetModel {
	private final SimpleObjectProperty<Pet> petProperty = new SimpleObjectProperty<>();
	
	 public void SavePet(Pet.Species species, Pet.Gender gender, String name) {
	        petProperty.set(new Pet(species, gender, name));
}
	
	public void deletePet() {
		petProperty.set(null);
	}
	
	public Pet getPet() {
		return petProperty.get();
	}
	
	public SimpleObjectProperty<Pet> petProperty() {
		return petProperty;
	}
}
