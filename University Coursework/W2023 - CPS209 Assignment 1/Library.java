import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	String errorMsg = "";
	
	public String getErrorMessage()
	{
		return errorMsg;
	}

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); ;
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public boolean download(AudioContent content)
	{
		String contentType = content.getType();

		if (contentType == "SONG") {
			for (Song song : songs) {
				if (content.equals(song)) {
					errorMsg = "Song Already Downloaded";
					return false;
				}
			}
			songs.add((Song)content);
			return true;
		}

		if (contentType == "AUDIOBOOK") {
			for (AudioBook book : audiobooks) {
				if (content.equals(book)) {
					errorMsg = "Audiobook Already Downloaded";
					return false;
				}
			}
			audiobooks.add((AudioBook)content);
			return true;
		}

		// RIP podcast
		errorMsg = "Invalid Audio Content Type";
		return false;
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for (int i = 0; i < audiobooks.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			audiobooks.get(i).printInfo();
			System.out.println();	
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for (int i = 0; i < playlists.size(); i++)
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.println(playlists.get(i).getTitle());
			System.out.println();	
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arraylist is complete, print the artists names

		ArrayList<String> artists = new ArrayList<String>();
		for (Song song : songs) {
			if (!artists.contains(song.getArtist())) {
				artists.add(song.getArtist());
			}

		}

		for (int i = 0; i < artists.size(); i++) {
			int index = i + 1;
			System.out.print("" + index + ". ");
			System.out.println(artists.get(i));
		}
		System.out.println();
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public boolean deleteSong(int index)
	{
		if (1 <= index && index <= songs.size()) {
			Song songToDelete = songs.get(index-1);

			for (Playlist playlist : playlists) {

				// while loop to remove multiple occurrences of songToDelete - just in case.
				while (playlist.getContent().remove(songToDelete)) { } // remove returns bool, true if removed, false if unchanged
			}
			songs.remove(index-1);
			return true;
			
		} 

		errorMsg = "Song Not Found";
		return false;
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());
	
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song song1, Song song2) {
			return (song1.getYear() - song2.getYear()); // way to get neg/0/pos values for less/equal/greater with less lines
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
		Collections.sort(songs, new SongLengthComparator());
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song song1, Song song2) {
			return (song1.getLength() - song2.getLength()); // way to get neg/0/pos values for less/equal/greater with less lines
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public boolean playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			errorMsg = "Song Not Found";
			return false;
		}
		songs.get(index-1).play();
		return true;
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public boolean playAudioBook(int index, int chapter)
	{
		if (index < 1 || index > audiobooks.size())
		{
			errorMsg = "Audiobook Not Found";
			return false;
		}

		AudioBook toPlay = audiobooks.get(index-1);

		if (chapter < 1 || chapter > toPlay.getNumberOfChapters()) {
			errorMsg = "Chapter Not Found";
			return false;
		}

		audiobooks.get(index-1).selectChapter(chapter);
		audiobooks.get(index-1).play();
		return true;
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public boolean printAudioBookTOC(int index)
	{
		if (index < 1 || index > audiobooks.size())
		{
			errorMsg = "Audiobook Not Found";
			return false;
		}
		audiobooks.get(index-1).printTOC();
		return true;
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public boolean makePlaylist(String title)
	{
		for (Playlist playlist : playlists) {
			if (title.equals(playlist.getTitle())) {
				errorMsg = "Playlist '" + title + "' Already Exists";
				return false;
			}
		}
		playlists.add(new Playlist(title));
		return true;
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public boolean printPlaylist(String title)
	{
		for (Playlist playlist : playlists) {
			if (title.equals(playlist.getTitle())) {
				playlist.printContents();
				return true;
			}
		}
		errorMsg = "Playlist Not Found";
		return false;
	}
	
	// Play all content in a playlist
	public boolean playPlaylist(String playlistTitle)
	{
		for (Playlist playlist : playlists) {
			if (playlistTitle.equals(playlist.getTitle())) {
				playlist.playAll();
				return true;
			}
		}

		errorMsg = "Playlist Not Found";
		return false;
	}
	
	// Play a specific song/audiobook in a playlist
	public boolean playPlaylist(String playlistTitle, int indexInPL)
	{
		for (Playlist playlist : playlists) { // iterate through playlists until reach right playlist
			if (playlistTitle.equals(playlist.getTitle())) {
				if (playlist.contains(indexInPL)) { // if index valid, play
					playlist.play(indexInPL);
					return true;
				} else { // else return and change errormsg
					errorMsg = "Index Invalid for Playlist";
					return false;
				}
			}
		}

		errorMsg = "Playlist Not Found";
		return false;
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public boolean addContentToPlaylist(String type, int index, String playlistTitle)
	{
		if (type.equalsIgnoreCase("SONG")) {
			for (Playlist playlist : playlists) {
				if (playlistTitle.equals(playlist.getTitle())) {
					playlist.addContent(songs.get(index-1));
					return true;
				}
			}
			errorMsg = "Playlist Not Found";
			return false;
		}

		if (type.equalsIgnoreCase("AUDIOBOOK")) {
			for (Playlist playlist : playlists) {
				if (playlistTitle.equals(playlist.getTitle())) {
					playlist.addContent(audiobooks.get(index-1));
					return true;
				}
			}
			errorMsg = "Playlist Not Found";
			return false;
		}

		errorMsg = "Audio Type Invalid";
		return false;
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public boolean delContentFromPlaylist(int index, String title)
	{
		Playlist targetPlaylist = null;
		
		for (Playlist playlist : playlists) {
			if (title.equals(playlist.getTitle())) {
				targetPlaylist = playlist;
			}
		}

		if (targetPlaylist == null) {
			errorMsg = "Audio Content Not Found";
			return false;
		}
		
		if (targetPlaylist.contains(index)) {
			targetPlaylist.deleteContent(index);
			return true;
		}

		errorMsg = "Index Invalid for Given Playlist";
		return false;
	}
	
}

