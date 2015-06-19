package com.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexBuilder {

	private static final String INDEX_DIR="lucene/indexes";
	
	public static void main(String[] args) {
		try {
			Directory dir = FSDirectory.open(new File(INDEX_DIR));
			Analyzer analyzer = new PaodingAnalyzer();//new StandardAnalyzer(Version.LUCENE_4_10_4);
			
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);

			boolean create = true;
			if (create) {
				iwc.setOpenMode(OpenMode.CREATE);
			} else {
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			IndexWriter writer = new IndexWriter(dir, iwc);

			writer.close();

		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass()
					+ "\n with message: " + e.getMessage());
		} finally {
			
		}
	}
	
}
