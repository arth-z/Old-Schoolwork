import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)
// Arthur Zeng - 501167672

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your library
		AudioContentStore store = new AudioContentStore();
		
		// Create my music library
		Library library = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				library.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				library.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				library.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				library.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				library.listAllPlaylists(); 
			}
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int startIndex = 0;
				int endIndex = 0;
				
				System.out.print("From Store Content #: ");
				if (scanner.hasNextInt())
				{
					startIndex = scanner.nextInt();
					scanner.nextLine(); // consume nl
				}

				System.out.print("To Store Content #: ");
				if (scanner.hasNextInt())
				{
					endIndex = scanner.nextInt();
					scanner.nextLine(); // consume nl
				}

				// no need for offsetI here because getContent(i) and start/endIndices are already all 1-offset
				for (int i = startIndex; i < endIndex+1; i++) {
					AudioContent content = store.getContent(i);
					if (content == null) {
						System.out.println("Content Number " + (i) + " Not Found in Store");
					} else {
						try {
							library.download(content); 
							System.out.println(i + ": " + content.getType() + " " + content.getTitle() + " Added to Library");	
						} catch (RedundantAudioContentException e) {
							System.out.println(i + ": " + e.getMessage());
						}
					}
				}
			}
			else if (action.equalsIgnoreCase("DOWNLOADA")) 
			{
				String creator = "";

				System.out.print("Content Author/Artist Name: ");
				if (scanner.hasNextLine()) 
				{
					creator = scanner.nextLine();
				}

				ArrayList<Integer> creatorIndices = store.getContentIndicesByCreator(creator);

				if (creatorIndices == null) {
					System.out.println("No Such Author/Artist In Store");
				} else {
					for (int i : creatorIndices) {
						int offsetI = i+1;
						AudioContent content = store.getContent(offsetI); // me when 1-offset
						if (content == null) {
							System.out.println(offsetI + ": " + "Content Invalid");
						} else {
							try {
								library.download(content); 
								System.out.println(offsetI + ": " + content.getType() + " " + content.getTitle() + " Added to Library");	
							} catch (RedundantAudioContentException e) {
								System.out.println(offsetI + ": " + e.getMessage());
							}
						}
					}
				}
			}
			else if (action.equalsIgnoreCase("DOWNLOADG")) 
			{
				String genre = "";

				System.out.print("Content Genre (POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL): ");
				if (scanner.hasNextLine()) 
				{
					genre = scanner.nextLine();
				}
				genre = genre.toUpperCase();

				ArrayList<Integer> genreIndices = store.getContentIndicesByGenre(genre);

				if (genreIndices == null) {
					System.out.println("No Such Genre In Store"); 
				} else {
					for (int i : genreIndices) {
						int offsetI = i+1;
						AudioContent content = store.getContent(offsetI); // me when 1-offset
						if (content == null) {
							System.out.println(offsetI + ": " + "Content Invalid");
						} else {
							try {
								library.download(content); 
								System.out.println(offsetI + ": " + content.getType() + " " + content.getTitle() + " Added to Library");	
							} catch (RedundantAudioContentException e) {
								System.out.println(offsetI + ": " + e.getMessage());
							}
						}
					}
				}
			}
			else if (action.equalsIgnoreCase("SEARCHA"))
			{
				String creator = "";

				System.out.print("Content Author/Artist Name: ");
				if (scanner.hasNextLine()) 
				{
					creator = scanner.nextLine();
				}

				store.listUsingCreator(creator);
			}
			else if (action.equalsIgnoreCase("SEARCHG"))
			{
				String genre = "";

				System.out.print("Content Genre (POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL): ");
				if (scanner.hasNextLine()) 
				{
					genre = scanner.nextLine();
				}

				genre = genre.toUpperCase();
				store.listUsingGenre(genre);
			}
			else if (action.equalsIgnoreCase("SEARCH"))
			{
				String title = "";

				System.out.print("Content Title: ");
				if (scanner.hasNextLine()) 
				{
					title = scanner.nextLine();
				}

				store.listUsingTitle(title);
			}
			else if (action.equalsIgnoreCase("SEARCHP"))
			{
				String string = "";

				System.out.print("Partial String: ");
				if (scanner.hasNextLine()) 
				{
					string = scanner.nextLine();
				}

				store.listUsingPartial(string);
			}
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				int index = 0;

				System.out.print("Song Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
				// consume the nl character since nextInt() does not
					scanner.nextLine(); 
				}
				try {
					library.playSong(index);
				} catch (AudioContentNotFoundException e) {
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				int index = 0;

				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					library.printAudioBookTOC(index);
				} catch (AudioContentNotFoundException e) {
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int index = 0;

				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
				}
				int chapter = 0;
				System.out.print("Chapter: ");
				if (scanner.hasNextInt())
				{
					chapter = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					library.playAudioBook(index, chapter);
				} catch (AudioContentNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (AudioSubcontentNotFoundException e) {
					System.out.println(e.getMessage());
				}
			}
			
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				int index = 0;
				int season = 0;
				
				System.out.print("Podcast Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
				}
				System.out.print("Season: ");
				if (scanner.hasNextInt())
				{
					season = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					library.printPodcastEpisodes(index, season);
				} catch (AudioContentNotFoundException e) {
					System.out.println(e.getMessage());	
				}
			}
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				int index = 0;

				System.out.print("Podcast Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				int season = 0;
				System.out.print("Season: ");
				if (scanner.hasNextInt())
				{
					season = scanner.nextInt();
					scanner.nextLine();
				}
				int episode = 0;
				System.out.print("Episode: ");
				if (scanner.hasNextInt())
				{
					episode = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					library.playPodcast(index, season, episode);
				} catch (AudioContentNotFoundException e) {
					System.out.println(e.getMessage());	
				} catch (AudioSubcontentNotFoundException e) {
					System.out.println(e.getMessage());	
				}
			}
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				String title = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				try {
					library.playPlaylist(title);
				} catch (PlaylistNotFoundException e) {
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				String title = "";
        		int index = 0;
        
				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				System.out.print("Content Number: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine();
				}
				try {
					library.playPlaylist(title, index); 
				} catch (PlaylistNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (InvalidPlaylistIndexException e) {
					System.out.println(e.getMessage());
				}
			}
			// Delete a song from the library and any play lists it belongs to
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int songNum = 0;

				System.out.print("Library Song #: ");
				if (scanner.hasNextInt())
				{
					songNum = scanner.nextInt();
					scanner.nextLine();
				}
				
				try {
					library.deleteSong(songNum);
				} catch (AudioContentNotFoundException e) {
					System.out.println(e.getMessage());	
				}
			}
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				String title = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
				{
					title = scanner.nextLine();
				}
				try {
					library.makePlaylist(title);
				} catch (RedundantPlaylistException e) {
					System.out.println(e.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String title = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine()) {
					title = scanner.nextLine();
				}

				try {
					library.printPlaylist(title);
				} catch (PlaylistNotFoundException e) {
					System.out.println(e.getMessage());
				}
			}
			// Add content from library (via index) to a playlist
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				int contentIndex = 0;
				String contentType = "";
        String playlist = "";
        
        System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
					playlist = scanner.nextLine();
        
				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
				if (scanner.hasNextLine())
					contentType = scanner.nextLine();
				
				System.out.print("Library Content #: ");
				if (scanner.hasNextInt())
				{
					contentIndex = scanner.nextInt();
					scanner.nextLine(); // consume nl
				}
				
				try {
					library.addContentToPlaylist(contentType, contentIndex, playlist);
				} catch (AudioContentNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (PlaylistNotFoundException e) {
					System.out.println(e.getMessage());
				}
			}
			// Delete content from play list
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				int contentIndex = 0;
				String playlist = "";

				System.out.print("Playlist Title: ");
				if (scanner.hasNextLine())
					playlist = scanner.nextLine();
				
				System.out.print("Playlist Content #: ");
				if (scanner.hasNextInt())
				{
					contentIndex = scanner.nextInt();
					scanner.nextLine(); // consume nl
				}
				try {
					library.delContentFromPlaylist(contentIndex, playlist);
				} catch (PlaylistNotFoundException e) {
					System.out.println(e.getMessage());	
				} catch (InvalidPlaylistIndexException e) {
					System.out.println(e.getMessage());	
				}
			}
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				library.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				library.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				library.sortSongsByLength();
			}

			System.out.print("\n>");
		}
	}
}
