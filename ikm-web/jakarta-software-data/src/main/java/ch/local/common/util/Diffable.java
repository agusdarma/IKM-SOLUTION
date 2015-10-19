package ch.local.common.util;

/**
 * Marker interface to tell ch.local.common.util.DifferenceFinder that your class is "diffable".
 * The DifferenceFinder eventually tries to get an unambigous id by calling the first get*Id method it finds (magic).
 * If you want to avoid the magic, use DiffablePrecise
 * 
 * @author Philipp Keller, local.ch AG
 * @version $Id: Location.java 14845 2006-12-19 15:52:12Z kay $
 * @since Mar 13, 2007
 */
public interface Diffable {
}