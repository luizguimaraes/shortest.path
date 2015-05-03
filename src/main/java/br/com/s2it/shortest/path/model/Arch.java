package br.com.s2it.shortest.path.model;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.h2.util.StringUtils.isNullOrEmpty;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;

@Entity
public class Arch implements Serializable {

	private static final long serialVersionUID = 2490963852451667075L;

	@Id
	@GeneratedValue
	private Integer id;

	@NotEmpty
	@Column(nullable = false, length = 10)
	private String begin;

	@NotEmpty
	@Column(nullable = false, length = 10)
	private String end;

	@NotNull
	@Min(value = 1)
	@Column(nullable = false)
	private int distance;

	@ManyToOne
	@JoinColumn(name = "map_id", insertable = false, updatable = false)
	private Map map;

	// Para uso do JPA
	protected Arch() {
	}

	@VisibleForTesting
	public Arch(@NotNull final String begin, @NotNull final String end,
			final int distance, @NotNull final Map map) {
		checkArgument(!isNullOrEmpty(begin), "begin cannot be null or empty");
		checkArgument(!isNullOrEmpty(end), "end cannot be null or empty");
		checkArgument(distance > 0, "distance must be greater than 0");
		this.begin = begin;
		this.end = end;
		this.distance = distance;
		this.map = checkNotNull(map, "map cannot be null");
	}

	public String getBegin() {
		return begin;
	}

	public String getEnd() {
		return end;
	}

	public int getDistance() {
		return distance;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(begin, end);
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof Arch)) {
			return false;
		}
		final Arch otherArch = (Arch) obj;
		return equal(otherArch.begin, begin) && equal(otherArch.end, end);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("begin", begin).add("end", end)
				.add("distance", distance).toString();
	}
}
