import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library
// Arthur Zeng - 501167672

public class AudioContentStore
{
		private ArrayList<AudioContent> contents; 
		private Map<String, ArrayList<Integer>> creatorMap;
		private Map<String, ArrayList<Integer>> genreMap;
		private Map<String, Integer> titleIndexMap;
		
		public AudioContentStore()
		{
			try {
				contents = makeContentsFromStore("store.txt");
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
			
			creatorMap = makeCreatorMap(contents);
			genreMap = makeGenreMap(contents);
			titleIndexMap = makeTitleIndexMap(contents);
		}

		private Map<String, ArrayList<Integer>> makeCreatorMap(ArrayList<AudioContent> contents) 
		{
			Map<String, ArrayList<Integer>> returnMap = new TreeMap<String, ArrayList<Integer>>();

			for (int i = 0; i < contents.size(); i++) {

				AudioContent content = contents.get(i);
				String creator = "";

				if (content.getType() == Song.TYPENAME) {
					Song song = (Song) content;
					creator = song.getArtist();
				} else if (content.getType() == AudioBook.TYPENAME) {
					AudioBook book = (AudioBook) content;
					creator = book.getAuthor();
				}

				ArrayList<Integer> creatorIndices = returnMap.get(creator);
				if (!returnMap.containsKey(creator)) {
					creatorIndices = new ArrayList<Integer>(); // redefine (it is null if this is the case)
					creatorIndices.add(i); // add first value

					returnMap.put(creator, creatorIndices); // put in the values
				} else {
					creatorIndices.add(i); // mutable value trolling 
				}
			}

			return returnMap;
		}

		private Map<String, ArrayList<Integer>> makeGenreMap(ArrayList<AudioContent> contents) 
		{
			Map<String, ArrayList<Integer>> returnMap = new TreeMap<String, ArrayList<Integer>>();

			for (int i = 0; i < contents.size(); i++) {

				AudioContent content = contents.get(i);
				String genre = "";

				if (content.getType().equals(Song.TYPENAME)) {
					Song song = (Song) content;
					genre = song.getGenre().toString();
				} else {
					genre = "No Genre/Not a Song"; // just adding this key for completeness (books dont have a genre field)
				}

				ArrayList<Integer> genreIndices = returnMap.get(genre);
				if (!returnMap.containsKey(genre)) {
					genreIndices = new ArrayList<Integer>(); // redefine (it is null if this is the case)
					genreIndices.add(i); // add first index
					returnMap.put(genre, genreIndices); // put in the values
				} else {
					genreIndices.add(i); // mutable value trolling
				}
			}


			return returnMap;
		}

		private Map<String, Integer> makeTitleIndexMap(ArrayList<AudioContent> contents) 
		{
			Map<String, Integer> returnMap = new TreeMap<String, Integer>();

			for (int i = 0; i < contents.size(); i++) {
				AudioContent content = contents.get(i);
				returnMap.put(content.getTitle(), i); 
			}

			return returnMap;
		}
		
		// method to read and generate AudioContent list from a file passed as input, throwing IOException to be caught in instructor
		private ArrayList<AudioContent> makeContentsFromStore(String fileName) throws IOException
		{
			ArrayList<AudioContent> returnList = new ArrayList<AudioContent>();

			File store = new File(fileName);
			Scanner storeReader = new Scanner(store);

			// basically read the file according to the given format, initialise AudioContent objects using file info, add to returnList
			while (storeReader.hasNextLine()) {
				String type = storeReader.nextLine();
				System.out.println("Loading " + type);
				if (type.equals("SONG")) {
					String id = storeReader.nextLine();
					String title = storeReader.nextLine();
					int year = Integer.parseInt(storeReader.nextLine());
					int length = Integer.parseInt(storeReader.nextLine());
					String artist = storeReader.nextLine();
					String composer = storeReader.nextLine();

					String genreString = storeReader.nextLine();
					Song.Genre genre = null;

					// do you want to write 5 different if statements for this enum? well, me neither!
					Song.Genre[] genreList = {Song.Genre.ROCK, Song.Genre.POP, Song.Genre.JAZZ, Song.Genre.HIPHOP, Song.Genre.RAP, Song.Genre.CLASSICAL};
					for (Song.Genre possibleGenre : genreList) {
						if (possibleGenre.toString().equals(genreString)) {
							genre = possibleGenre;
						}
					}

					// since lyrics are multiple lines, this grabs lyric lines based on the lyricCount line and makes it based on that
					String lyrics = "";
					int lyricLength = Integer.parseInt(storeReader.nextLine());

					for (int i = 0; i < lyricLength; i++) {
						lyrics += storeReader.nextLine() + "\r\n";
					}

					//bringing it all together
					Song newSong = new Song(title, year, id, Song.TYPENAME, lyrics, length, artist, composer, genre, lyrics);
					returnList.add(newSong);
				} 
				
				else if (type.equals("AUDIOBOOK")) { // audiobook follows the same methodology as Song
					String id = storeReader.nextLine();
					String title = storeReader.nextLine();
					int year = Integer.parseInt(storeReader.nextLine());
					int length = Integer.parseInt(storeReader.nextLine());
					String author = storeReader.nextLine();
					String narrator = storeReader.nextLine();

					int chapterCount = Integer.parseInt(storeReader.nextLine());

					ArrayList<String> chapterTitles = new ArrayList<String>();

					for (int i = 0; i<chapterCount; i++) {
						String chapterTitle = storeReader.nextLine();
						chapterTitles.add(chapterTitle);
					}

					// nested loop to save multiple chapters based on their line count and chapter count
					ArrayList<String> chapters = new ArrayList<String>();
					for (int i = 0; i < chapterCount; i++) {
						int chapterLineCount = Integer.parseInt(storeReader.nextLine());
						String chapter = "";
						for (int j = 0; j < chapterLineCount; j++) {
							chapter += storeReader.nextLine() + "\r\n";
						}
						chapters.add(chapter);
					}

					AudioBook newAudiobook = new AudioBook(title, year, id, AudioBook.TYPENAME, title, length, author, narrator, chapterTitles, chapters);
					returnList.add(newAudiobook);


				}
			}

			storeReader.close(); // god, stop complaining to me VSCode
			return returnList;
		}
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}

		public ArrayList<Integer> getContentIndicesByCreator(String creator)
		{
			return creatorMap.get(creator);
		}

		public ArrayList<Integer> getContentIndicesByGenre(String genre)
		{
			return genreMap.get(genre);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print(index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}

		public void listUsingCreator(String creator) 
		{
			if (!creatorMap.keySet().contains(creator)) {
				System.out.println("No Such Artist/Author In Store");
				return;
			}
			// iterate through arraylist of ints using creator as key
			for (int i : creatorMap.get(creator)) {
				int index = i + 1;
				System.out.print(index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}

		public void listUsingGenre(String genre)
		{
			if (!genreMap.keySet().contains(genre)) {
				System.out.println("No Such Genre In Store");
				return;
			}
			// iterate through arraylist of ints using genre as key
			for (int i : genreMap.get(genre)) {
				int index = i + 1;
				System.out.print(index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		public void listUsingTitle(String title) 
		{
			if (!titleIndexMap.keySet().contains(title)) {
				System.out.println("No matches for " + title);
			}
			int i = titleIndexMap.get(title);
			System.out.print((i+1) + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}

		public void listUsingPartial(String string) 
		{
			// if i wanted a 'fast' search i'd have to make a dict for EVERYSTRINGCONTENTEVER : indices
			// which is uh, sorry but no thanks, so i'll just stick iterating through and hope it turns out ok

			for (AudioContent content : contents) {

				String title = ""; // title of the successful 'hit' - successes will change this to an actual title, which we can use to call listUsingTitle

				// look through superclass (AudioContent native fields), see if we get any hits
				String[] superclassContents = {content.getTitle(), content.getAudioFile(), content.getId(), content.getType()};
				for (String contentInfo : superclassContents) {
					if (contentInfo.contains(string)) {
						title = content.getTitle(); 
					}
				}

				// go by Song/Audiobook, same deal
				if (content.getType().equals(Song.TYPENAME)) {

					Song song = (Song) content;

					// arraylist because lyrics are variable and also exist
					String[] songContents = {song.getArtist(), song.getLyrics(), song.getComposer(), song.getGenre().toString()};

					for (String songInfo : songContents) {
						if (songInfo.contains(string)) {
							title = content.getTitle(); 
						}
					}

				} else if (content.getType().equals(AudioBook.TYPENAME)) {

					AudioBook book = (AudioBook) content;

					// arraylist because chapter titles are variable and also exist
					ArrayList<String> bookInfo = new ArrayList<String>();
					bookInfo.addAll(book.getChapterTitles());
					bookInfo.addAll(book.getChapters());

					String[] nonChapterInfo = {book.getAuthor(), book.getNarrator()};

					for (String info : nonChapterInfo) {
						bookInfo.add(info);
					}

					for (String info : bookInfo) {
						if (info.contains(string)) {
							title = content.getTitle(); 
						}
					}
				}

				if (!title.equals("")) { // if the index was changed then we got a hit, print it out
					listUsingTitle(title);
				}
			}
		}
}
