import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

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
			
			else if (action.equalsIgnoreCase("STORE"))	// List all audio content in store
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all audiobooks
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all podcasts
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all artists
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all playlists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int index = 0;
				
				System.out.print("Store Content #: ");
				if (scanner.hasNextInt())
				{
					index = scanner.nextInt();
					scanner.nextLine(); // "consume" nl character (necessary when mixing nextLine() and nextInt())
				}
				AudioContent content = store.getContent(index);
				if (content == null)
					System.out.println("Content Not Found in Store");
				else if (!mylibrary.download(content))
						System.out.println(mylibrary.getErrorMessage());
									
			}
			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				int index = 0;

				System.out.print("Song Number: ");
				if (scanner.hasNextInt()) {
					index = scanner.nextInt();
					scanner.nextLine();
				}

				// Print error message if the song doesn't exist in the library
				if (!mylibrary.playSong(index)) {
					System.out.println(mylibrary.getErrorMessage());
				}

			}
			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				int index = 0;

				System.out.print("Audiobook Number: ");
				if (scanner.hasNextInt()) {
					index = scanner.nextInt();
					scanner.nextLine();
				}

				// Print error message if the book doesn't exist in the library
				if (!mylibrary.printAudioBookTOC(index)) {
					System.out.println(mylibrary.getErrorMessage());
				}
			}
			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int index = 0;
				int chapter = 0;

				System.out.print("Book Number: ");
				if (scanner.hasNextInt()) {
					index = scanner.nextInt();
					scanner.nextLine();
				}

				System.out.print("Chapter: ");
				if (scanner.hasNextInt()) {
					chapter = scanner.nextInt();
					scanner.nextLine();
				}

				// Print error message if the audiobook doesn't exist in the library
				if (!mylibrary.playAudioBook(index, chapter)) {
					System.out.println(mylibrary.getErrorMessage());
				}
			}
			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				
			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				
			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				String title = "";

				System.out.print("Playlist Title: ");
				title = scanner.nextLine();

				// Print error message if the playlist doesn't exist in the library
				if (!mylibrary.playPlaylist(title)) {
					System.out.println(mylibrary.getErrorMessage());
				}
			}
			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				String title = "";
				int index = 0;

				System.out.print("Playlist Title: ");
				title = scanner.nextLine();

				System.out.print("Content Number: ");
				if (scanner.hasNextInt()) {
					index = scanner.nextInt();
					scanner.nextLine();
				}

				// Print error message if the playlist doesn't exist in the library
				if (!mylibrary.playPlaylist(title, index)) {
					System.out.println(mylibrary.getErrorMessage());
				}
			}
			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				int index = 0;

				System.out.print("Song Number: ");
				if (scanner.hasNextInt()) {
					index = scanner.nextInt();
					scanner.nextLine();
				}

				// Print error message if the song doesn't exist in the library
				if (!mylibrary.deleteSong(index)) {
					System.out.println(mylibrary.getErrorMessage());
				}
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				String title = "";

				System.out.print("Playlist Title: ");
				title = scanner.nextLine();

				// Print error message if the playlist already exists in the library
				if (!mylibrary.makePlaylist(title)) {
					System.out.println(mylibrary.getErrorMessage());
				}
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				String title = "";

				System.out.print("Playlist Title: ");
				title = scanner.nextLine();

				// Print error message if the playlist already exists in the library
				if (!mylibrary.printPlaylist(title)) {
					System.out.println(mylibrary.getErrorMessage());
				}
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				String title = "";
				String type = "";
				int index = 0;

				System.out.print("Playlist Title: ");
				title = scanner.nextLine();

				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
				if (scanner.hasNext()) {
					type = scanner.next();
					scanner.nextLine();
				}

				System.out.print("Library Content #: ");
				if (scanner.hasNextInt()) {
					index = scanner.nextInt();
					scanner.nextLine();
				}

				// Print error message if the playlist already exists in the library
				if (!mylibrary.addContentToPlaylist(type, index, title)) {
					System.out.println(mylibrary.getErrorMessage());
				}
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				String title = "";
				int index = 0;

				System.out.print("Playlist Title: ");
				title = scanner.nextLine();

				System.out.print("Playlist Content #: ");
				if (scanner.hasNextInt()) {
					index = scanner.nextInt();
					scanner.nextLine();
				}

				// Print error message if *something* goes wrong (either playlist invalid or index invalid)
				if (!mylibrary.delContentFromPlaylist(index, title)) {
					System.out.println(mylibrary.getErrorMessage());
				}
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}

			System.out.print("\n>");
		}
	}
}
