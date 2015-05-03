package br.com.s2it.shortest.path.model;

import static com.google.common.base.Objects.equal;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;

@Entity
public class Map {

	@Id
	@GeneratedValue
	private Integer id;

	@NotEmpty
	@Column(nullable = true)
	private String name;

	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "map_id", nullable = false, foreignKey = @ForeignKey(name = "arch_map_fk"))
	private List<Arch> arches;

	@VisibleForTesting
	public Map() {
	}

	public String getName() {
		return name;
	}

	public List<Arch> getArches() {
		return arches;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Map && equal(((Map) obj).name, name);
	}
}
