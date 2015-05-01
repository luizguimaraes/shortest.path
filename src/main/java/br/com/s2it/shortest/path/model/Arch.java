package br.com.s2it.shortest.path.model;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotNull;

import com.google.common.base.Objects;

@Entity
@IdClass(Arch.ArchId.class)
public class Arch implements Serializable {

	public static class ArchId implements Serializable {

		private static final long serialVersionUID = 3370333180130223920L;

		private String begin;
		private String end;

		public String getBegin() {
			return begin;
		}

		public void setBegin(final String begin) {
			this.begin = begin;
		}

		public String getEnd() {
			return end;
		}

		public void setEnd(final String end) {
			this.end = end;
		}

		@Override
		public int hashCode() {
			return Objects.hashCode(begin, end);
		}

		@Override
		public boolean equals(final Object obj) {
			if (!(obj instanceof ArchId)) {
				return false;
			}
			final ArchId otherArch = (ArchId) obj;
			return otherArch.begin.equals(begin) && otherArch.end.equals(end);
		}
	}

	private static final long serialVersionUID = -8963009267710119185L;

	@Id
	@Column(nullable = false)
	private String begin;
	@Id
	@Column(nullable = false)
	private String end;

	@Column(nullable = false)
	private int distance;

	// Para uso do JPA
	protected Arch() {
	}

	public Arch(@NotNull final String begin, @NotNull final String end,
			final int distance) {
		checkArgument(begin != null && !begin.isEmpty(),
				"begin cannot be nnull or empty");
		checkArgument(end != null && !end.isEmpty(),
				"end cannot be nnull or empty");
		checkArgument(distance > 0, "distance must be greater than 0");
		this.begin = begin;
		this.end = end;
		this.distance = distance;
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
		return otherArch.begin.equals(begin) && otherArch.end.equals(end);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("begin", begin).add("end", end)
				.add("distance", distance).toString();
	}
}
