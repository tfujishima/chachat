package jp.gmo.ojt.chachat.domain.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@SuppressWarnings("serial")
public class CanvasDrawHistory implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank
	@Embedded
    private CanvasIdentity canvasIdentity;
	@NotBlank
	private String targetId;
	@NotBlank
	private String mode;
	@NotBlank
	private String color;
	@NotNull
	private Integer lineWidth;
	@NotNull
	private Integer fromX;
	@NotNull
	private Integer fromY;
	@NotNull
	private Integer toX;
	@NotNull
	private Integer toY;
	
    @NotNull
    private Date createdAt;

	@PrePersist
	protected void onCreate() {
	  this.createdAt = new Date();
	}
}
