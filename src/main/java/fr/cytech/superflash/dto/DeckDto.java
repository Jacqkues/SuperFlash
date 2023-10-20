package fr.cytech.superflash.dto;
import fr.cytech.superflash.entity.Matiere;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeckDto {
	private long id;
	
	@NotEmpty(message="name shouldn't be empty")
	private String name;
	@NotEmpty(message="description shouldn't be empty")
	private String description;
	@NotEmpty(message="matiere shouldn't be empty")
	private Matiere matiere;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Matiere getMatiere() {
		return matiere;
	}
	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}
}