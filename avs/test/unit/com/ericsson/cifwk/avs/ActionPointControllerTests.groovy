package com.ericsson.cifwk.avs



import org.junit.*
import grails.test.mixin.*

@TestFor(ActionPointController)
@Mock(ActionPoint)
class ActionPointControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/actionPoint/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.actionPointInstanceList.size() == 0
        assert model.actionPointInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.actionPointInstance != null
    }

    void testSave() {
        controller.save()

        assert model.actionPointInstance != null
        assert view == '/actionPoint/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/actionPoint/show/1'
        assert controller.flash.message != null
        assert ActionPoint.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/actionPoint/list'


        populateValidParams(params)
        def actionPoint = new ActionPoint(params)

        assert actionPoint.save() != null

        params.id = actionPoint.id

        def model = controller.show()

        assert model.actionPointInstance == actionPoint
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/actionPoint/list'


        populateValidParams(params)
        def actionPoint = new ActionPoint(params)

        assert actionPoint.save() != null

        params.id = actionPoint.id

        def model = controller.edit()

        assert model.actionPointInstance == actionPoint
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/actionPoint/list'

        response.reset()


        populateValidParams(params)
        def actionPoint = new ActionPoint(params)

        assert actionPoint.save() != null

        // test invalid parameters in update
        params.id = actionPoint.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/actionPoint/edit"
        assert model.actionPointInstance != null

        actionPoint.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/actionPoint/show/$actionPoint.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        actionPoint.clearErrors()

        populateValidParams(params)
        params.id = actionPoint.id
        params.version = -1
        controller.update()

        assert view == "/actionPoint/edit"
        assert model.actionPointInstance != null
        assert model.actionPointInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/actionPoint/list'

        response.reset()

        populateValidParams(params)
        def actionPoint = new ActionPoint(params)

        assert actionPoint.save() != null
        assert ActionPoint.count() == 1

        params.id = actionPoint.id

        controller.delete()

        assert ActionPoint.count() == 0
        assert ActionPoint.get(actionPoint.id) == null
        assert response.redirectedUrl == '/actionPoint/list'
    }
}
