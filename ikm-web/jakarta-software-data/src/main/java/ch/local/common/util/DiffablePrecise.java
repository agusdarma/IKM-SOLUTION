package ch.local.common.util;

/**
 * @see Diffable
 * 
 * @author Philipp Keller, local.ch AG
 * @version $Id$
 * @since Mar 13, 2007
 */
public interface DiffablePrecise extends Diffable {
	
	/**
	 * @return unambigous id of this instance. In most cases, this is the
	 *         database id or LocalUID. In some cases you have to construct
	 *         a unique id that is generated only if the object has the
	 *         current status.
	 */
	public String getDiffableId();

}
