package fr.cytech.superflash.dto;


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
    
	private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    private Long matiereId;
    
    public Long getMatiereId(){
    	return matiereId;
    }
    
    public void setMatiereId(Long id) {
    	this.matiereId = id;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

}