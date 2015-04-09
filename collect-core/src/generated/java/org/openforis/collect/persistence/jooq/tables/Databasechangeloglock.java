/**
 * This class is generated by jOOQ
 */
package org.openforis.collect.persistence.jooq.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Databasechangeloglock extends org.jooq.impl.TableImpl<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord> {

	private static final long serialVersionUID = -946085088;

	/**
	 * The singleton instance of <code>collect.databasechangeloglock</code>
	 */
	public static final org.openforis.collect.persistence.jooq.tables.Databasechangeloglock DATABASECHANGELOGLOCK = new org.openforis.collect.persistence.jooq.tables.Databasechangeloglock();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord> getRecordType() {
		return org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord.class;
	}

	/**
	 * The column <code>collect.databasechangeloglock.id</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>collect.databasechangeloglock.locked</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord, java.lang.Boolean> LOCKED = createField("locked", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

	/**
	 * The column <code>collect.databasechangeloglock.lockgranted</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord, java.sql.Timestamp> LOCKGRANTED = createField("lockgranted", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

	/**
	 * The column <code>collect.databasechangeloglock.lockedby</code>.
	 */
	public final org.jooq.TableField<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord, java.lang.String> LOCKEDBY = createField("lockedby", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * Create a <code>collect.databasechangeloglock</code> table reference
	 */
	public Databasechangeloglock() {
		this("databasechangeloglock", null);
	}

	/**
	 * Create an aliased <code>collect.databasechangeloglock</code> table reference
	 */
	public Databasechangeloglock(java.lang.String alias) {
		this(alias, org.openforis.collect.persistence.jooq.tables.Databasechangeloglock.DATABASECHANGELOGLOCK);
	}

	private Databasechangeloglock(java.lang.String alias, org.jooq.Table<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord> aliased) {
		this(alias, aliased, null);
	}

	private Databasechangeloglock(java.lang.String alias, org.jooq.Table<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, org.openforis.collect.persistence.jooq.Collect.COLLECT, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord> getPrimaryKey() {
		return org.openforis.collect.persistence.jooq.Keys.PK_DATABASECHANGELOGLOCK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.openforis.collect.persistence.jooq.tables.records.DatabasechangeloglockRecord>>asList(org.openforis.collect.persistence.jooq.Keys.PK_DATABASECHANGELOGLOCK);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.openforis.collect.persistence.jooq.tables.Databasechangeloglock as(java.lang.String alias) {
		return new org.openforis.collect.persistence.jooq.tables.Databasechangeloglock(alias, this);
	}

	/**
	 * Rename this table
	 */
	public org.openforis.collect.persistence.jooq.tables.Databasechangeloglock rename(java.lang.String name) {
		return new org.openforis.collect.persistence.jooq.tables.Databasechangeloglock(name, null);
	}
}
