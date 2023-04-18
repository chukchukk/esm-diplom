package io.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class EsmFile implements Serializable {

	private String id;

	private String name;

}
