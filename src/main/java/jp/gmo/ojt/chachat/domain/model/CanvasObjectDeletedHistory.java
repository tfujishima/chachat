package jp.gmo.ojt.chachat.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="canvas_object_deleted_histories")
@SuppressWarnings("serial")
public class CanvasObjectDeletedHistory extends CanvasHistory{

}
