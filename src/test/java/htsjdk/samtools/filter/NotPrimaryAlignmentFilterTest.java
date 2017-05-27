package htsjdk.samtools.filter;

import htsjdk.HtsjdkTest;
import htsjdk.samtools.SAMRecordSetBuilder;
import junit.framework.Assert;
import org.junit.Test;

import static org.testng.Assert.*;

/**
 * Created by farjoun on 5/27/17.
 */
public class NotPrimaryAlignmentFilterTest extends HtsjdkTest {

    @Test
    public void testSecondaryRecords() {
        SAMRecordSetBuilder builder = new SAMRecordSetBuilder();
        int i = 0;
        for (boolean record1Unmapped : new boolean[]{true, false}) {
            for (boolean record2Unmapped : new boolean[]{true, false}) {
                for (boolean record1Strand : new boolean[]{true, false}) {
                    for (boolean record2Strand : new boolean[]{true, false}) {

                        builder.addPair("pair" + i, 0, 10, 30,
                                record1Unmapped, record2Unmapped,
                                null, null,
                                record1Strand, record2Strand,
                                true, true,
                                10);
                        builder.addFrag("frag" + i++, 0, 10,
                                record1Unmapped,
                                record2Strand,
                                null,null,
                                10,true);

                    }
                }
            }
        }

        FilteringSamIterator filteringSamIterator = new FilteringSamIterator(builder.getRecords().iterator(),
                new NotPrimaryAlignmentFilter());

        Assert.assertEquals(filteringSamIterator.hasNext(),false);
    }

    @Test
    public void testPrimaryRecords() {
        SAMRecordSetBuilder builder = new SAMRecordSetBuilder();
        int i = 0;
        for (boolean record1Unmapped : new boolean[]{true, false}) {
            for (boolean record2Unmapped : new boolean[]{true, false}) {
                for (boolean record1Strand : new boolean[]{true, false}) {
                    for (boolean record2Strand : new boolean[]{true, false}) {

                        builder.addPair("pair" + i, 0, 10, 30,
                                record1Unmapped, record2Unmapped,
                                null, null,
                                record1Strand, record2Strand,
                                false, false,
                                10);
                        builder.addFrag("frag" + i++, 0, 10,
                                record1Unmapped,
                                record2Strand,
                                null,null,
                                10,false);
                    }
                }
            }
        }

        FilteringSamIterator filteringSamIterator = new FilteringSamIterator(builder.getRecords().iterator(),
                new NotPrimaryAlignmentFilter());

        Assert.assertEquals(filteringSamIterator.stream().count(),i*3);
    }



    @Test
    public void testSupplementaryRecords() {
        SAMRecordSetBuilder builder = new SAMRecordSetBuilder();
        int i = 0;
        for (boolean record1Unmapped : new boolean[]{true, false}) {
            for (boolean record2Unmapped : new boolean[]{true, false}) {
                for (boolean record1Strand : new boolean[]{true, false}) {
                    for (boolean record2Strand : new boolean[]{true, false}) {

                        builder.addPair("pair" + i, 0, 10, 30,
                                record1Unmapped, record2Unmapped,
                                null, null,
                                record1Strand, record2Strand,
                                false, false,
                                10).stream().forEach(r->r.setSupplementaryAlignmentFlag(true));
                        builder.addFrag("frag" + i++, 0, 10,
                                record1Unmapped,
                                record2Strand,
                                null,null,
                                10,false).setSupplementaryAlignmentFlag(true);
                    }
                }
            }
        }

        FilteringSamIterator filteringSamIterator = new FilteringSamIterator(builder.getRecords().iterator(),
                new NotPrimaryAlignmentFilter());

        Assert.assertEquals(filteringSamIterator.stream().count(),i*3);
    }
}

