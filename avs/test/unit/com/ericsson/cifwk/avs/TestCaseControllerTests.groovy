package com.ericsson.cifwk.avs



import org.junit.*
import grails.test.mixin.*

@TestFor(TestCaseController)
@Mock(TestCase)
class TestCaseControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/testCase/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.testCaseInstanceList.size() == 0
        assert model.testCaseInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.testCaseInstance != null
    }

    void testSave() {
        controller.save()

        assert model.testCaseInstance != null
        assert view == '/testCase/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/testCase/show/1'
        assert controller.flash.message != null
        assert TestCase.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/testCase/list'


        populateValidParams(params)
        def testCase = new TestCase(params)

        assert testCase.save() != null

        params.id = testCase.id

        def model = controller.show()

        assert model.testCaseInstance == testCase
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/testCase/list'


        populateValidParams(params)
        def testCase = new TestCase(params)

        assert testCase.save() != null

        params.id = testCase.id

        def model = controller.edit()

        assert model.testCaseInstance == testCase
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/testCase/list'

        response.reset()


        populateValidParams(params)
        def testCase = new TestCase(params)

        assert testCase.save() != null

        // test invalid parameters in update
        params.id = testCase.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/testCase/edit"
        assert model.testCaseInstance != null

        testCase.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/testCase/show/$testCase.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        testCase.clearErrors()

        populateValidParams(params)
        params.id = testCase.id
        params.version = -1
        controller.update()

        assert view == "/testCase/edit"
        assert model.testCaseInstance != null
        assert model.testCaseInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/testCase/list'

        response.reset()

        populateValidParams(params)
        def testCase = new TestCase(params)

        assert testCase.save() != null
        assert TestCase.count() == 1

        params.id = testCase.id

        controller.delete()

        assert TestCase.count() == 0
        assert TestCase.get(testCase.id) == null
        assert response.redirectedUrl == '/testCase/list'
    }
}
