package ch.local.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.BeanUtils;

import ch.local.common.exception.DifferenceException;

/**
 * By providing getDifference() this class offers a generic way to find out
 * differences of two objects of any class.
 * 
 * @author Philipp Keller, local.ch AG
 * @version $Id$
 * @since Mar 13, 2007
 */
public class DifferenceFinder {
	/**
	 * compare differences between Object lhs and rhs recursively. Returns a
	 * HashMap in which the keys are the field names, path inclusive a returning
	 * HashMap could be (python notation)
	 * 
	 * {"addresses.name": ["differs"], "categories": ["id 345 is missing in left
	 * object", "id 34 is missing in right object"]}
	 * 
	 * @param exclude
	 *            say which fields shouldn't be checked, e.g. ["categories",
	 *            "addresses.internal_field"] if you exclude a field from the
	 *            check, it's sub-fields won't be checked either
	 * 
	 * @throws DifferenceException
	 */
	public static HashMap<String, Collection<String>> getDifferences(Object lhs, Object rhs, Collection<String> exclude) throws DifferenceException {
		return getDifferences(lhs, rhs, "", exclude, new HashMap<String, Collection<String>>());
	}

	/**
	 * @see getDifferences(Object lhs, Object rhs, CollectionK<String> exclude)
	 */
	public static HashMap<String, Collection<String>> getDifferences(Object lhs, Object rhs) throws DifferenceException {
		return getDifferences(lhs, rhs, "", new LinkedList<String>(), new HashMap<String, Collection<String>>());
	}

	/**
	 * convenience method to check that two objects have the same state
	 */
	public static boolean isSame(Object lhs, Object rhs) throws DifferenceException {
		return (getDifferences(lhs, rhs).keySet().size() == 0);
	}

	private static HashMap<String, Collection<String>> getDifferences(Object lhs, Object rhs, String path, Collection<String> exclude, HashMap<String, Collection<String>> differencesSoFar)
			throws DifferenceException {
		if (lhs == null) {
			if (rhs == null) {
				return differencesSoFar;
			} else {
				addDifference("left object is null, right object isn't", "", path, differencesSoFar);
				return differencesSoFar;
			}
		} else if (rhs == null) {
			addDifference("right object is null, left object isn't", "", path, differencesSoFar);
			return differencesSoFar;

		}
		if (!lhs.getClass().equals(rhs.getClass())) {
			throw new DifferenceException("you cannot compare an object of type " + lhs.getClass().getName() + " with an object of type " + rhs.getClass().getName());
		}

		PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(lhs.getClass());
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if (!exclude.contains(propertyDescriptor.getName())) {
				try {
					if (propertyDescriptor.getReadMethod() == null) {
						throw new IllegalAccessException("couldn't access the read method");
					}
					Object lhsValue = propertyDescriptor.getReadMethod().invoke(lhs, new Object[] {});
					Object rhsValue = propertyDescriptor.getReadMethod().invoke(rhs, new Object[] {});
					if (lhsValue instanceof Collection) {
						// in a collection, first get a hashmap of all the keys
						// of the objects and compare those hashmaps and then
						// compare the objects themself
						if (lhsValue == null) {
							if (rhsValue != null) {
								addDifference("differs (Collecion): left value=null, right value=\"" + String.valueOf(rhsValue) + "\"", propertyDescriptor.getName(), path, differencesSoFar);
							}
						} else if (rhsValue == null) {
							if (lhsValue != null) {
								addDifference("differs (Collecion): left value=\"" + String.valueOf(lhsValue) + "\", right value=null", propertyDescriptor.getName(), path, differencesSoFar);
							}
						} else {
							HashMap<String, Object> lhsMap = collection2hashMap((Collection) lhsValue, propertyDescriptor.getName());
							HashMap<String, Object> rhsMap = collection2hashMap((Collection) rhsValue, propertyDescriptor.getName());
							if (!lhsMap.keySet().equals(rhsMap.keySet())) {
								for (String keyOri : lhsMap.keySet()) {
									if (!rhsMap.containsKey(keyOri)){
										System.out.println("KeyOri: " + keyOri);
										if(keyOri.length() > 2)
											addDifference(keyOri+ "," + "-" , propertyDescriptor.getName(), path, differencesSoFar);
									}// minus
								}
								for (String keyUpdated : rhsMap.keySet()) {
									if (!lhsMap.containsKey(keyUpdated)){
										System.out.println("KeyUpdated: " +keyUpdated);
										if(keyUpdated.length() > 2)
											addDifference("-" + "," + keyUpdated , propertyDescriptor.getName(), path, differencesSoFar);
									}// add
								}
								
								/*
								Set<String> lhsMinusRhs = lhsMap.keySet();
								lhsMinusRhs.removeAll(rhsMap.keySet());
								for (String key : lhsMinusRhs) {
//									addDifference(lhsMap.get(key).getClass() + " with key " + key + " is missing in right instance", propertyDescriptor.getName(), path, differencesSoFar);
									addDifference("minus " + key , propertyDescriptor.getName(), path, differencesSoFar);
								}
								Set<String> rhsMinusLhs = rhsMap.keySet();
								rhsMinusLhs.removeAll(lhsMap.keySet());
								for (String key : rhsMinusLhs) {
									addDifference(rhsMap.get(key).getClass() + " with key " + key + " is missing in left instance", propertyDescriptor.getName(), path, differencesSoFar);
//									addDifference(key + " is missing in left instance", propertyDescriptor.getName(), path, differencesSoFar);
								}
								*/
							}

							Set<String> keysInBoth = lhsMap.keySet();
							keysInBoth.retainAll(rhsMap.keySet());
							for (String key : keysInBoth) {
								String fieldPath = path;
								if (fieldPath.length() > 0) {
									fieldPath += ".";
								}
								fieldPath += propertyDescriptor.getName();

								// create a new exclude list suitable for sub
								// element
								Collection<String> subExclude = new LinkedList<String>();
								for (String exel : exclude) {
									if (exel.startsWith(propertyDescriptor.getName() + ".")) {
										subExclude.add(exel.substring((propertyDescriptor.getName() + ".").length()));
									}
								}

								differencesSoFar = getDifferences(lhsMap.get(key), rhsMap.get(key), fieldPath, subExclude, differencesSoFar);
							}
						}
					} else {
						// if property is not a collection then rely on
						// EqualsBuilder which seems quite complete
						if (!new EqualsBuilder().append(lhsValue, rhsValue).isEquals()) {
//							addDifference("differs: left value=\"" + String.valueOf(lhsValue) + "\", right value=\"" + String.valueOf(rhsValue) + "\"", propertyDescriptor.getName(), path,
//									differencesSoFar);
							addDifference(String.valueOf(lhsValue) + ";" + String.valueOf(rhsValue), propertyDescriptor.getName(), path,
									differencesSoFar);
						}
					}
				} catch (InvocationTargetException e) {
					throw new DifferenceException("couldn't invoke the getter of property " + propertyDescriptor.getDisplayName() + " of an object of type " + lhs.getClass().getName());
				} catch (IllegalAccessException e) {
					throw new DifferenceException("the access to property " + propertyDescriptor.getDisplayName() + " of an object of type " + lhs.getClass().getName() + " was refused");
				}
			}
		}
		return differencesSoFar;
	}

	private static HashMap<String, Object> collection2hashMap(Collection c, String fieldname) throws DifferenceException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		for (Object lhsCollectionElement : c) {
			if (lhsCollectionElement instanceof Diffable) {
				String id = null;
				if (lhsCollectionElement instanceof DiffablePrecise) {
					id = ((DiffablePrecise) lhsCollectionElement).getDiffableId();
				} else {
					PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(lhsCollectionElement.getClass());
					PropertyDescriptor shortestIdProperty = null;
					for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
						if (propertyDescriptor.getName().endsWith("Id") && (shortestIdProperty == null || shortestIdProperty.getName().length() > propertyDescriptor.getName().length())) {
							shortestIdProperty = propertyDescriptor;
						}
					}
					if (shortestIdProperty == null) {
						throw new DifferenceException(fieldname + " is a collection that has got elements of type " + lhsCollectionElement.getClass().getName()
								+ ". This does implement Diffable but doesn't have a get*Id()-Property");
					} else {
						try {
							Object idObject = shortestIdProperty.getReadMethod().invoke(lhsCollectionElement, new Object[] {});
							if (idObject == null) {
								throw new DifferenceException("the getter for property " + shortestIdProperty.getName() + " on object of class " + lhsCollectionElement.getClass().getName()
										+ " returned null. Because DifferenceFinder relys on a proper id you might consider implementing the DiffablePrecise interface in "
										+ lhsCollectionElement.getClass().getName());
							}
							id = idObject.toString();
						} catch (InvocationTargetException e) {
							throw new DifferenceException("couldn't invoke the getter of property " + shortestIdProperty.getDisplayName() + " of an object of type "
									+ lhsCollectionElement.getClass().getName());
						} catch (IllegalAccessException e) {
							throw new DifferenceException("the access to property " + shortestIdProperty.getDisplayName() + " of an object of type " + lhsCollectionElement.getClass().getName()
									+ " was refused");
						}
					}

				}
				if (result.containsKey(id)) {
					throw new DifferenceException(fieldname + " is a collection of type " + lhsCollectionElement.getClass().getName() + " which has two elements with the same id \"" + id
							+ "\". That's a no no!");
				}
				result.put(id, lhsCollectionElement);
			} else if (lhsCollectionElement instanceof Comparable) {
				for (Object o : c) {
					result.put(String.valueOf(o), o);
				}
			} else {
				throw new DifferenceException(fieldname + " is a collection that has got elements of type " + lhsCollectionElement.getClass().getName() + ". This doesn't implement Diffable");
			}
		}
		return result;
	}

	/**
	 * add a difference entry into differencesSoFar
	 * 
	 * @param message
	 * @param fieldname
	 * @param path
	 * @param differencesSoFar
	 */
	private static void addDifference(String message, String fieldname, String path, HashMap<String, Collection<String>> differencesSoFar) {
		String fqn = path;
		if (fqn.length() > 0) {
			fqn += ".";
		}
		fqn += fieldname;
		if (differencesSoFar.containsKey(fqn)) {
			differencesSoFar.get(fqn).add(message);
		} else {
			Collection<String> tmp = new LinkedList<String>();
			tmp.add(message);
			differencesSoFar.put(fqn, tmp);
		}
	}
}