package au.com.noojee.acceloapi.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import au.com.noojee.acceloapi.entities.AcceloEntity;
import au.com.noojee.acceloapi.entities.meta.fieldTypes.FilterField;

class AfterOrEq<E extends AcceloEntity<E>, DT> extends Expression
{

	private FilterField<E, DT> field;
	private DT operand;

	public AfterOrEq(FilterField<E, DT> field, DT localDate)
	{
		this.field = field;
		this.operand = localDate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toJson()
	{
		if (operand instanceof LocalDate)
			return (new After<>(field, operand)
					.or(new Eq<>((FilterField<E, LocalDate>) field, (LocalDate) operand))).toJson();

		return (new After<>(field, operand)
					.or(new Eq<>((FilterField<E, LocalDateTime>) field, (LocalDateTime) operand))).toJson();
	}

	@Override
	public Expression copy()
	{
		return new AfterOrEq<>(field, operand);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((operand == null) ? 0 : operand.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		AfterOrEq<E, DT> other = (AfterOrEq<E, DT>) obj;
		if (field == null)
		{
			if (other.field != null)
				return false;
		}
		else if (!field.equals(other.field))
			return false;
		if (operand == null)
		{
			if (other.operand != null)
				return false;
		}
		else if (!operand.equals(other.operand))
			return false;
		return true;
	}

}