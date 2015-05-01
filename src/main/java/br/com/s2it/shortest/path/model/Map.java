package br.com.s2it.shortest.path.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.google.common.annotations.VisibleForTesting;

@Entity
public class Map {

	@Id
	private int id;

	@Column(nullable = true)
	private String name;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "map_id", nullable = false, foreignKey = @ForeignKey(name = "arch_map_fk"))
	private Set<Arch> arches;

	@VisibleForTesting
	public Map() {
	}

	public Set<Arch> getArches() {
		return arches;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Map && ((Map) obj).name.equals(name);
	}
}
