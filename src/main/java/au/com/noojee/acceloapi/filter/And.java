package au.com.noojee.acceloapi.filter;

class And extends Expression
{

	private Expression parent;
	private Expression child;

	// Required for serialization
	public And()
	{
	}
	
	public And(Expression parent, Expression child)
	{
		super();
		this.parent = parent;
		this.child = child;
	}

	
	@Override
	public Expression copy()
	{
		And and = new And(this.parent, this.child);
		
		return and;
	}

	@Override
	public String toJson()
	{
		String expression = "\"_AND\": {\n" + parent.toJson() + "," + child.toJson() + "\n}";

		return expression;

	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
		And other = (And) obj;
		if (child == null)
		{
			if (other.child != null)
				return false;
		}
		else if (!child.equals(other.child))
			return false;
		if (parent == null)
		{
			if (other.parent != null)
				return false;
		}
		else if (!parent.equals(other.parent))
			return false;
		return true;
	}
}
