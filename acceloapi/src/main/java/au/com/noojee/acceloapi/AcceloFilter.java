package au.com.noojee.acceloapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AcceloFilter
{
	public static final String ALL = "_ALL";
	
	public interface Match
	{
		public String toJson();
	}


	ArrayList<Match> matches = new ArrayList<Match>();

	private Search search = null;

	public AcceloFilter add(Search search) throws AcceloException
	{
		if (matches.size() > 0)
			throw new AcceloException("You may not combine filters and searches");
		
		this.search = search;
		return this;

	}

	public CompoundMatch add(AcceloFilter.CompoundMatch match) throws AcceloException
	{
		if (search != null)
			throw new AcceloException("You may not combine filters and searches");

		matches.add(match);
		return match;
	}

	
	public AcceloFilter add(AcceloFilter.SimpleMatch match) throws AcceloException
	{
		if (search != null)
			throw new AcceloException("You may not combine filters and searches");

		matches.add(match);
		return this;
	}

	/**
	 * Takes a map of filter key/value pairs and builds a json filter
	 * 
	 * "_filters": { "email": ["pepper@test.com", "salt@test.com"] }
	 * 
	 * @param filterMap
	 * @return
	 */
	public String toJson()
	{
		String json = "";
		boolean firstFilter = true;

		for (Match match: matches)
		{
			if (firstFilter)
			{
				json += "\"_filters\": {\n";
				firstFilter = false;
			}
			else
				json += ",";

			json += match.toJson();

		}
		if (!firstFilter)
			json += "}";


		if (search != null)
		{
			json += "\"_search\": ";
			json += "\"" + search.operand + "\"";
		}

		return json;
	}

	static public class Search
	{
		private String operand;

		public Search(String operand)
		{
			this.operand = operand;
		}

		public String toJson()
		{
			String json = "";

			json += "\"_search\": ";

			json += "\"" + operand + "\"";

			return json;
		}
	}

	static public class SimpleMatch implements Match
	{
	
		private String fieldName;
		private List<String> operands = new ArrayList<>();
	
		public SimpleMatch(String fieldName, int operand)
		{
			this.fieldName = fieldName;
			this.operands.add("" + operand);
		}
	
		public SimpleMatch(String fieldName, String operand)
		{
			this.fieldName = fieldName;
			this.operands.add("" + operand);
		}
	
		public SimpleMatch(String fieldName, String[] operand)
		{
			this.fieldName = fieldName;
			this.operands.addAll(Arrays.asList(operand));
		}
	
		public String toJson()
		{
			String json = "";
	
			json += "\"" + fieldName + "\": [";
	
			boolean firstOperand = true;
			for (String operand : operands)
			{
				if (firstOperand)
					firstOperand = false;
				else
					json += ",";
	
				json += "\"" + operand + "\"";
			}
			json += "]";
	
			return json;
	
		}
	}

	static public class CompoundMatch implements Match
	{
	
		private String fieldName;
		private List<Match> matches = new ArrayList<>();
	
		
		public CompoundMatch(String fieldName)
		{
			this.fieldName = fieldName;
		}
		public CompoundMatch(String fieldName, Match match)
		{
			this.fieldName = fieldName;
			this.matches.add(match);
		}
	
		public CompoundMatch(String fieldName, Match[] match)
		{
			this.fieldName = fieldName;
			this.matches.addAll(Arrays.asList(match));
		}
		
		public CompoundMatch add(Match match)
		{
			this.matches.add(match);
			return this;
		}
	
		
		
	
		public String toJson()
		{
			String json = "";
	
			json += "\"" + fieldName + "\": [{";
	
			boolean firstOperand = true;
			for (Match match : matches)
			{
				if (firstOperand)
					firstOperand = false;
				else
					json += ",";
	
				json += match.toJson();
			}
			json += "}]";
	
			return json;
	
		}
	}
	
	public String toString()
	{
		return toJson();
	}

}