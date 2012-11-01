
public class Word 
{
	private String title;
	private String[] synonyms;
	
	public Word(String title,String[] synonyms)
	{
		this.title = title;
		this.synonyms = synonyms;
	}
	
	public String getTitle(){ return this.title; }
	public String[] getSynonyms(){ return this.synonyms; }
}